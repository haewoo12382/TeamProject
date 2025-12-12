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

        // "안녕하세요" 이미지
        ImageIcon helloIcon = new ImageIcon("resources/images/hello.png");
        Image scaledImage = helloIcon.getImage().getScaledInstance(400, 185, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel helloLabel = new JLabel(scaledIcon, SwingConstants.CENTER);
        helloLabel.setBounds(440, 200, 400, 185); // 위치와 크기를 setBounds로 맞춤
        add(helloLabel);

        // 팀원 아바타 이미지
        ImageIcon peopleIcon = new ImageIcon("resources/images/people.png");
        Image scaledPeople = peopleIcon.getImage().getScaledInstance(700, 324, Image.SCALE_SMOOTH);
        ImageIcon scaledPeopleIcon = new ImageIcon(scaledPeople);
        JLabel peopleLabel = new JLabel(scaledPeopleIcon, SwingConstants.CENTER);
        peopleLabel.setBounds(290, 360, 700, 324);
        add(peopleLabel);

        // Next 버튼 이미지
        ImageIcon nextIcon = new ImageIcon("resources/images/next.png"); // 이미지 파일 경로
        Image scaledNext = nextIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon scaledNextIcon = new ImageIcon(scaledNext);
        JButton nextBtn = new JButton(scaledNextIcon);
        nextBtn.setBounds(1200, 610, 60, 60);
        nextBtn.setBorderPainted(false);  // 버튼 테두리 제거
        nextBtn.setContentAreaFilled(false); // 버튼 배경 제거
        nextBtn.setFocusPainted(false); // 포커스 표시 제거
        add(nextBtn);

        // Next 버튼 이벤트
        nextBtn.addActionListener(e -> parent.showScreen("file"));

    }
}