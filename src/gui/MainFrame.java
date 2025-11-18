package gui;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        // 1. 창의 기본 설정 (InitWindow 역할)
        setTitle("미지지향 프로그래밍");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 450);

        // 2. 우리가 만든 'MainPanel'을 가져와서 붙이기
        setContentPane(new MainPanel());

        // 3. 보여주기
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}