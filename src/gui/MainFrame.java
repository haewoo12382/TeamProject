package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout layout = new CardLayout();
    private JPanel container = new JPanel(layout);

    public MainFrame() {

        // 기본 프레임 설정
        setTitle("미지지향프로그래밍");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 중앙 배치
        setResizable(false);

        container.add(new IntroPanel(this), "intro");
        container.add(new FileSelectPanel(this), "file");
        container.add(new SimulationPanel(this), "sim");
        /*container.add(new ResultPanel(this), "result");
        container.add(new EndPanel(this), "end");*/

        add(container);

        //첫화면 표시
        layout.show(container, "intro");

        setVisible(true);
    }

    public void showScreen(String name) {
        layout.show(container, name);
    }
}

/*
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
}*/