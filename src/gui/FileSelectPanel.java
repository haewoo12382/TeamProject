package gui;

import common.ReadText;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class FileSelectPanel extends JPanel {

    public FileSelectPanel(MainFrame parent) {

        setLayout(null);
        setBackground(Color.WHITE);

        // ======================
        // 1) 파일 선택 영역
        // ======================
        JTextField txtSelect = new JTextField("txt. 선택하기");
        txtSelect.setBounds(100, 80, 600, 50);
        txtSelect.setEditable(false);
        add(txtSelect);

        JButton arrowDownBtn = new JButton("▼");
        arrowDownBtn.setBounds(700, 80, 40, 50);
        add(arrowDownBtn);

        //다음버튼
        ImageIcon originalIcon = new ImageIcon("resources/images/next.png");

        Image img = originalIcon.getImage();
        Image scaledImg = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon nextIcon = new ImageIcon(scaledImg);

        JButton nextBtn = new JButton(nextIcon);
        nextBtn.setBounds(1200, 610, 60, 60);
        nextBtn.setBorderPainted(false);  // 버튼 테두리 제거
        nextBtn.setContentAreaFilled(false); // 버튼 배경 제거
        nextBtn.setFocusPainted(false); // 포커스 표시 제거
        add(nextBtn);

        nextBtn.addActionListener(e -> {
            String currentFile = txtSelect.getText();

            // 유효성 검사: 초기 문구("txt. 선택하기") 그대로이거나 비어있는 경우
            if (currentFile.equals("txt. 선택하기") || currentFile.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "진행할 파일을 선택해주세요.", "알림", JOptionPane.WARNING_MESSAGE);
                return;
            }
            parent.showScreen("sim");
        });

        // ======================
        // 2) 미리보기 토글 버튼
        // ======================
        JButton previewToggleBtn = new JButton("미리보기 ▼");
        previewToggleBtn.setBounds(100, 150, 140, 40);
        previewToggleBtn.setBackground(new Color(240, 225, 80));
        add(previewToggleBtn);

        // ======================
        // 3) 미리보기 영역 (스크롤 포함)
        // ======================
        JTextArea previewText = new JTextArea();
        previewText.setEditable(false);
        previewText.setBackground(new Color(200, 205, 215));

        JScrollPane previewScroll = new JScrollPane(
                previewText,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        previewScroll.setBounds(100, 200, 1080, 430);
        add(previewScroll);

        final boolean[] previewVisible = {true};

        // ======================
        // 4) 토글 버튼 기능
        // ======================
        previewToggleBtn.addActionListener(e -> {
            previewVisible[0] = !previewVisible[0];

            previewScroll.setVisible(previewVisible[0]);

            if (previewVisible[0]) {
                previewToggleBtn.setText("미리보기 ▼");
                previewToggleBtn.setBackground(new Color(240, 225, 80));
            } else {
                previewToggleBtn.setText("미리보기 ▲");
                previewToggleBtn.setBackground(new Color(160, 160, 160));
            }
        });

        // ======================
        // 7) 파일 목록 불러오기 기능 (resources/text)
        // ======================
        arrowDownBtn.addActionListener(e -> {

            File dir = new File("resources/text");

            if (!dir.exists() || !dir.isDirectory()) {
                JOptionPane.showMessageDialog(this, "resources/text 폴더를 찾을 수 없습니다.");
                return;
            }

            File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));

            if (files == null || files.length == 0) {
                JOptionPane.showMessageDialog(this, "txt 파일이 없습니다.");
                return;
            }

            // 파일명 목록 만들기
            String[] fileNames = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                fileNames[i] = files[i].getName();
            }

            // 팝업 리스트 띄우기
            String selected = (String) JOptionPane.showInputDialog(
                    this,
                    "파일 선택",
                    "txt 파일 목록",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    fileNames,
                    fileNames[0]
            );

            if (selected != null) {
                txtSelect.setText(selected);
                ReadText.selectedFile = selected;   // ★ 선택한 파일 저장

                String fileName = txtSelect.getText().trim();
                if (fileName.equals("txt. 선택하기") || fileName.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "먼저 파일을 선택하세요.");
                    return;
                }

                File file = new File("resources/text/" + fileName);

                if (!file.exists()) {
                    JOptionPane.showMessageDialog(this, "파일이 존재하지 않습니다.");
                    return;
                }

                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    br.close();

                    previewText.setText(sb.toString());
                    previewText.setCaretPosition(0); // 맨 위로 스크롤
                } catch (Exception ex) {
                    previewText.setText("파일 읽기 오류: " + ex.getMessage());
                }

            }
        });

    }
}
