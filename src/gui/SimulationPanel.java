package gui;

import algorithm.Fifo;
import algorithm.Priority;
import algorithm.RunState;
import algorithm.StepListener;
import common.ReadText;
import model.Process;
import model.Result;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class SimulationPanel extends JPanel implements StepListener {

    // 상태 변수
    private String mode = "FIFO";          // FIFO / PRIORITY
    private int speedMultiplier = 1;       // x1, x5, x10, x100
    private volatile boolean isPlaying = false;   // 현재 재생 중 여부

    private int totalProcessCount = 0;     // 전체 프로세스 개수
    private int currentCompleted = 0;      // 현재까지 완료된 프로세스 수

    // 알고리즘 제어 상태
    private RunState runState = null;

    // UI 컴포넌트
    private JLabel percentLabel;

    private ImageIcon pikaNormal, pikaActive;
    private JLabel pikaLabel;

    private ImageIcon batEmpty, batRed, batYellow, batGreen;
    private JLabel[] batteryLabels = new JLabel[10];

    // 대기큐 / 실행큐 PID 표시
    private JLabel[] waitQueueLabels = new JLabel[10];
    private JLabel[] runQueueLabels  = new JLabel[10];

    private JTextArea resultArea;
    private JButton playBtn;
    private JButton stopBtn;

    public SimulationPanel(MainFrame parent) {

        setLayout(null);
        setBackground(Color.WHITE);

        // 이미지 로딩
        pikaNormal = new ImageIcon(getClass().getResource("/images/pika.png"));
        // 더 밝게
        pikaActive = changeBrightness(pikaNormal, +40);

        batEmpty  = new ImageIcon(getClass().getResource("/images/empty_bat.png"));
        batRed    = new ImageIcon(getClass().getResource("/images/red_bat.png"));
        batYellow = new ImageIcon(getClass().getResource("/images/yellow_bat.png"));
        batGreen  = new ImageIcon(getClass().getResource("/images/green_bat.png"));

        // LEFT PANEL
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBackground(new Color(245, 245, 245));
        leftPanel.setBounds(0, 0, 320, 720);
        add(leftPanel);

        percentLabel = new JLabel("100%", SwingConstants.CENTER);
        percentLabel.setBounds(50, 40, 220, 80);
        percentLabel.setOpaque(true);
        percentLabel.setBackground(new Color(230, 230, 230));
        leftPanel.add(percentLabel);

        // 모드 선택 버튼
        JButton fifoBtn = new JButton("FIFO");
        fifoBtn.setBounds(50, 150, 100, 60);
        fifoBtn.setBackground(new Color(200, 230, 255));
        leftPanel.add(fifoBtn);

        JButton priorityBtn = new JButton("Priority");
        priorityBtn.setBounds(170, 150, 100, 60);
        priorityBtn.setBackground(new Color(230, 230, 230));
        leftPanel.add(priorityBtn);

        fifoBtn.addActionListener(e -> {
            mode = "FIFO";
            fifoBtn.setBackground(new Color(200, 230, 255));
            priorityBtn.setBackground(new Color(230, 230, 230));
        });

        priorityBtn.addActionListener(e -> {
            mode = "PRIORITY";
            priorityBtn.setBackground(new Color(200, 230, 255));
            fifoBtn.setBackground(new Color(230, 230, 230));
        });

        // 배속 선택
        String[] speeds = {"x1", "x5", "x10", "x100"};
        JComboBox<String> speedBox = new JComboBox<>(speeds);
        speedBox.setBounds(50, 240, 220, 40);
        leftPanel.add(speedBox);

        speedBox.addActionListener(e -> {
            switch ((String) speedBox.getSelectedItem()) {
                case "x5"   -> speedMultiplier = 5;
                case "x10"  -> speedMultiplier = 10;
                case "x100" -> speedMultiplier = 100;
                default     -> speedMultiplier = 1;
            }
        });

        // 재생 / 정지 버튼
        playBtn = new JButton("▶");
        playBtn.setBounds(50, 310, 100, 60);
        leftPanel.add(playBtn);

        stopBtn = new JButton("■");
        stopBtn.setBounds(170, 310, 100, 60);
        leftPanel.add(stopBtn);

        // CENTER PANEL
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(null);
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBounds(320, 0, 704, 720);
        add(centerPanel);

        // 상단 대기큐 표시
        JPanel waitQueuePanel = new JPanel(new GridLayout(1, 10, 5, 5));
        waitQueuePanel.setBounds(20, 20, 664, 40);
        waitQueuePanel.setBackground(new Color(235, 235, 235));
        centerPanel.add(waitQueuePanel);

        for (int i = 0; i < 10; i++) {
            JLabel lbl = new JLabel("-", SwingConstants.CENTER);
            lbl.setOpaque(true);
            lbl.setBackground(new Color(210, 210, 210));
            waitQueueLabels[i] = lbl;
            waitQueuePanel.add(lbl);
        }

        // 피카츄 패널 (중앙)
        JPanel pikachuPanel = new JPanel();
        int pikaW = 200;
        int pikaH = 200;
        int centerWidth = 704;
        int pikaX = (centerWidth - pikaW) / 2;
        pikachuPanel.setBounds(pikaX, 80, pikaW, pikaH);
        pikachuPanel.setOpaque(false);
        pikachuPanel.setBackground(null);
        centerPanel.add(pikachuPanel);

        pikaLabel = new JLabel(pikaNormal);
        pikachuPanel.add(pikaLabel);

        // 배터리 패널
        JPanel batteryPanel = new JPanel(new GridLayout(1, 10, 5, 5));
        batteryPanel.setBounds(20, 320, 664, 120);
        batteryPanel.setBackground(new Color(240, 240, 240));
        centerPanel.add(batteryPanel);

        for (int i = 0; i < 10; i++) {
            JLabel bat = new JLabel(batEmpty);
            batteryLabels[i] = bat;
            batteryPanel.add(bat);
        }

        // 실행큐 PID 표시
        JPanel runQueuePanel = new JPanel(new GridLayout(1, 10, 5, 5));
        runQueuePanel.setBounds(20, 460, 664, 40);
        runQueuePanel.setBackground(new Color(235, 235, 235));
        centerPanel.add(runQueuePanel);

        for (int i = 0; i < 10; i++) {
            JLabel lbl = new JLabel("-", SwingConstants.CENTER);
            lbl.setOpaque(true);
            lbl.setBackground(new Color(210, 210, 210));
            runQueueLabels[i] = lbl;
            runQueuePanel.add(lbl);
        }

        // RIGHT PANEL
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBackground(new Color(245, 245, 245));
        rightPanel.setBounds(1024, 0, 256, 720);
        add(rightPanel);

        JLabel resultLabel = new JLabel("결과 미리보기");
        resultLabel.setBounds(20, 20, 200, 30);
        rightPanel.add(resultLabel);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane resultScroll = new JScrollPane(resultArea);
        resultScroll.setBounds(20, 60, 216, 550);
        rightPanel.add(resultScroll);

        // ===============================
        // 버튼 동작
        // ===============================

        // 재생 버튼: 재생/일시정지 토글
        playBtn.addActionListener(e -> {
            if (!isPlaying) {
                // 현재 재생 중이 아님
                // 1) 아직 실행한 적 없거나, 이전 실행이 끝난 상태 → 새로 시작
                if (runState == null || runState.finished || runState.aborted) {
                    runState = new RunState();
                    isPlaying = true;

                    playBtn.setText("Ⅱ");
                    playBtn.setBackground(new Color(255, 230, 180));

                    resetUIBeforeRun();
                    totalProcessCount = ReadText.getProcessList("").size();
                    currentCompleted = 0;
                    percentLabel.setText("100%");

                    new Thread(() -> {
                        if (mode.equals("FIFO")) {
                            Fifo.run(SimulationPanel.this, runState);
                        } else {
                            Priority.run(SimulationPanel.this, runState);
                        }
                    }).start();

                } else if (runState.paused && !runState.finished) {
                    // 2) 일시정지 상태에서 다시 재생 → 이어서 진행
                    runState.paused = false;
                    isPlaying = true;

                    playBtn.setText("Ⅱ");
                    playBtn.setBackground(new Color(255, 230, 180));
                }

            } else {
                // 현재 재생 중 → 일시정지
                isPlaying = false;
                if (runState != null) {
                    runState.paused = true;
                }
                playBtn.setText("▶");
                playBtn.setBackground(null);
            }
        });

        // 정지 버튼: 완전 정지 + 초기화
        stopBtn.addActionListener(e -> {
            if (runState != null) {
                runState.aborted = true;
                runState.paused = false;
            }
            isPlaying = false;
            playBtn.setText("▶");
            playBtn.setBackground(null);
            resetUIAll();
        });
    }

    // ============================================================
    // StepListener 구현부
    // ============================================================

    @Override
    public void onStep(int runIndex, List<Process> waitQueue, List<Process> runQueue, Process executing) {

        // 강제 중단이면 무시
        if (runState != null && runState.aborted) return;

        // 일시정지 상태면 onStep은 호출 안 되는 게 정상이지만, 방어적으로 한 번 더 확인
        if (!isPlaying) return;

        // 1) 대기큐 PID 업데이트
        updateWaitQueueUI(waitQueue);

        // 2) 실행큐 PID 업데이트
        updateRunQueueUI(runQueue);

        // 3) 배터리 + 피카츄 애니메이션
        animateStep(runIndex, executing.getProcessTime(), speedMultiplier);

        // 4) 퍼센트 업데이트
        currentCompleted++;
        if (totalProcessCount > 0) {
            int percent = 100 - (int) (((double) currentCompleted / totalProcessCount) * 100);
            if (percent < 0) percent = 0;
            final int finalPercent = percent;
            SwingUtilities.invokeLater(() -> percentLabel.setText(finalPercent + "%"));
        }
    }

    @Override
    public void onFinish(Result result) {

        // 강제 중단이면 결과 표시하지 않음
        if (runState != null && runState.aborted) {
            isPlaying = false;
            return;
        }

        if (runState != null) {
            runState.finished = true;
        }

        SwingUtilities.invokeLater(() -> {
            playBtn.setText("▶");
            playBtn.setBackground(null);

            percentLabel.setText("0%");

            // 실행큐 / 배터리 정리 (P100 등 남지 않도록)
            for (int i = 0; i < 10; i++) {
                runQueueLabels[i].setText("-");
                batteryLabels[i].setIcon(batEmpty);
            }

            resultArea.setText(
                    "[알고리즘] " + result.name + "\n"
                            + "총 실행시간 : " + result.totalProcessTime + "\n"
                            + "총 대기시간 : " + result.totalWaitTime + "\n"
                            + "평균 실행시간 : " + result.averageProcessTime + "\n"
                            + "평균 대기시간 : " + result.averageWaitTime + "\n"
                            + "최장 프로세스 : P" + result.longProcess.id + " (" + result.longProcess.processTime + ")\n"
                            + "최단 프로세스 : P" + result.shortProcess.id + " (" + result.shortProcess.processTime + ")\n"
                            + "총 프로세스 개수 : " + result.totalSize + "\n"
            );
        });

        isPlaying = false;
    }

    // ============================================================
    // UI 업데이트 함수들
    // ============================================================

    private void updateWaitQueueUI(List<Process> waitQueue) {
        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < 10; i++) {
                if (i < waitQueue.size() && waitQueue.get(i) != null) {
                    waitQueueLabels[i].setText(waitQueue.get(i).id);
                } else {
                    waitQueueLabels[i].setText("-");
                }
            }
        });
    }

    private void updateRunQueueUI(List<Process> runQueue) {
        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < 10; i++) {
                if (i < runQueue.size() && runQueue.get(i) != null) {
                    runQueueLabels[i].setText(runQueue.get(i).id);
                } else {
                    runQueueLabels[i].setText("-");
                }
            }
        });
    }

    // 배터리 + 피카츄 애니메이션
    private void animateStep(int runIndex, int runtime, int speed) {
        try {
            if (runState != null && runState.aborted) return;

            SwingUtilities.invokeLater(() -> pikaLabel.setIcon(pikaActive));

            long baseDelay = Math.max(1, runtime * 250L / speed);

            // Red
            if (runState != null && runState.aborted) return;
            while (runState != null && runState.paused && !runState.aborted) {
                Thread.sleep(10);
            }
            if (runState != null && runState.aborted) return;
            SwingUtilities.invokeLater(() -> batteryLabels[runIndex].setIcon(batRed));
            Thread.sleep(baseDelay);

            // Yellow
            if (runState != null && runState.aborted) return;
            while (runState != null && runState.paused && !runState.aborted) {
                Thread.sleep(10);
            }
            if (runState != null && runState.aborted) return;
            SwingUtilities.invokeLater(() -> batteryLabels[runIndex].setIcon(batYellow));
            Thread.sleep(baseDelay);

            // Green
            if (runState != null && runState.aborted) return;
            while (runState != null && runState.paused && !runState.aborted) {
                Thread.sleep(10);
            }
            if (runState != null && runState.aborted) return;
            SwingUtilities.invokeLater(() -> batteryLabels[runIndex].setIcon(batGreen));
            Thread.sleep(baseDelay);

            if (runState != null && runState.aborted) return;

            SwingUtilities.invokeLater(() -> pikaLabel.setIcon(pikaNormal));
            Thread.sleep(Math.max(1, 200L / speed));
            SwingUtilities.invokeLater(() -> batteryLabels[runIndex].setIcon(batEmpty));

        } catch (InterruptedException ignored) {
        }
    }

    // 실행 시작 전에 UI 리셋
    private void resetUIBeforeRun() {
        SwingUtilities.invokeLater(() -> {
            resultArea.setText("");
            percentLabel.setText("100%");
            for (int i = 0; i < 10; i++) {
                batteryLabels[i].setIcon(batEmpty);
                waitQueueLabels[i].setText("-");
                runQueueLabels[i].setText("-");
            }
            pikaLabel.setIcon(pikaNormal);
        });
    }

    // 정지 버튼 눌렀을 때 전체 초기화
    private void resetUIAll() {
        currentCompleted = 0;
        SwingUtilities.invokeLater(() -> {
            percentLabel.setText("100%");
            resultArea.setText("");
            for (int i = 0; i < 10; i++) {
                batteryLabels[i].setIcon(batEmpty);
                waitQueueLabels[i].setText("-");
                runQueueLabels[i].setText("-");
            }
            pikaLabel.setIcon(pikaNormal);
        });
    }

    // 이미지 밝기 조정
    private ImageIcon changeBrightness(ImageIcon icon, int amount) {
        Image img = icon.getImage();

        BufferedImage buff = new BufferedImage(
                img.getWidth(null),
                img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = buff.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();

        for (int y = 0; y < buff.getHeight(); y++) {
            for (int x = 0; x < buff.getWidth(); x++) {
                int rgba = buff.getRGB(x, y);
                Color col = new Color(rgba, true);

                int r = clamp(col.getRed() + amount);
                int gg = clamp(col.getGreen() + amount);
                int b = clamp(col.getBlue() + amount);

                Color newCol = new Color(r, gg, b, col.getAlpha());
                buff.setRGB(x, y, newCol.getRGB());
            }
        }

        return new ImageIcon(buff);
    }

    private int clamp(int v) {
        if (v < 0) return 0;
        if (v > 255) return 255;
        return v;
    }
}
