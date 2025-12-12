package gui;

import javax.swing.*;
import java.awt.*;

public class IntroPanel extends JPanel {

    public IntroPanel(MainFrame parent) {
        setLayout(null);                // 절대좌표 사용
        setBackground(Color.WHITE);     // 배경 색깔

        // 상단 타이틀바
        JPanel titleBar = new JPanel();
        titleBar.setLayout(null);       // 절대좌표 사용
        titleBar.setBackground(new Color(40, 40, 40));
        titleBar.setBounds(0, 0, 1280, 60);
        add(titleBar);

        JLabel titleLabel = new JLabel("미지지향프로그래밍");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(20, 15, 300, 30);
        titleBar.add(titleLabel);

        // 중앙 "안녕하세요"
        JLabel helloLabel = new JLabel("안녕하세요", SwingConstants.CENTER);
        helloLabel.setBounds(440, 200, 400, 100);
        helloLabel.setFont(new Font("SansSerif", Font.PLAIN, 35));
        add(helloLabel);

        // 사람 4명 박스 영역
        JPanel peopleBox = new JPanel();
        peopleBox.setBounds(290, 360, 700, 180);
        peopleBox.setBackground(new Color(20, 90, 120)); // 너가 준 색 느낌
        add(peopleBox);

        // Next 버튼
        JButton nextBtn = new JButton("→");
        nextBtn.setBounds(1170, 610, 80, 80);
        add(nextBtn);

        // Next 버튼 이벤트
        nextBtn.addActionListener(e -> parent.showScreen("file"));

    }
}