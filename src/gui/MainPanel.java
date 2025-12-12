package gui;

import common.ReadText;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import model.Process;
import java.util.List;

public class MainPanel extends JPanel {

    private JComboBox<String> fileSelectBox;
    private JList<String> fileList;
    private DefaultListModel<String> listModel;
    private JButton runButton; // '실행' 버튼 추가

    private static final String DIR_PATH = "resources/text/";

    public MainPanel() {
        // 1. 레이아웃 설정 (자신의 레이아웃)
        setLayout(new BorderLayout());

        // 2. UI 만들기
        initTopPanel();      // 상단 (콤보박스)
        initCenterList();    // 중앙 (리스트)
        initBottomPanel();   // 하단 (실행 버튼) - ★ 새로 추가된 부분

        // 3. 이벤트 연결
        addListeners();
    }

    private void initTopPanel() {
        JPanel topPanel = new JPanel();
        String[] items = {"파일 업로드", "파일 선택"};
        fileSelectBox = new JComboBox<>(items);
        topPanel.add(fileSelectBox);

        // 'this'는 이 패널(MainPanel) 자신을 의미하므로 바로 add 가능
        add(topPanel, BorderLayout.NORTH);
    }

    private void initCenterList() {
        listModel = new DefaultListModel<>();
        fileList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(fileList);

        add(scrollPane, BorderLayout.CENTER);
    }
    // 하단 패널 및 실행 버튼 생성 메소드
    private void initBottomPanel() {
        // FlowLayout.RIGHT를 쓰면 버튼이 오른쪽으로 정렬됩니다.
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        runButton = new JButton("실행");
        runButton.setPreferredSize(new Dimension(80, 30)); // 버튼 크기 살짝 조정

        bottomPanel.add(runButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        fileSelectBox.addActionListener(e -> {
            String selected = (String) fileSelectBox.getSelectedItem();
            listModel.clear();
            if (selected.equals("파일 선택")) {
                loadTxtFiles();
            }
        });

        // 실행 버튼 클릭 시 로직
        runButton.addActionListener(e -> {
            // 리스트에서 선택된 항목 가져오기
            String selectedFileName = fileList.getSelectedValue();

            // 선택된 파일이 없을 경우 경고창 띄우기 (예외 처리)
            if (selectedFileName == null) {
                JOptionPane.showMessageDialog(this, "먼저 리스트에서 파일을 선택해주세요!", "알림", JOptionPane.WARNING_MESSAGE);
                return; // 함수 종료
            }

            // 실행 로직
            String fullPath = DIR_PATH + selectedFileName;
            System.out.println("실행 요청: " + fullPath);

            // 데이터 불러오기
            List<Process> processList = ReadText.getProcessList(fullPath);

            // 결과 확인 (콘솔)
            System.out.println("--- 로드 완료 (" + processList.size() + "개) ---");
            for (Process p : processList) {
                System.out.println("Process: " + p.getId());
            }

            // 결과 알림
            //JOptionPane.showMessageDialog(this, selectedFileName + " 데이터 로드 완료!");
        });
    }

    private void loadTxtFiles() {
        // ... 아까 그 파일 로딩 로직 그대로 ...
        File dir = new File(DIR_PATH);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            Arrays.sort(files);
            for (int i=0; i<files.length; i++) {
                if(files[i].getName().endsWith(".txt")){
                    listModel.addElement(files[i].getName());
                }
            }
        }
    }
}