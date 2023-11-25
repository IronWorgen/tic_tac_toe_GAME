package org.example.game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsWindow extends JFrame {
    public static final int WINDOW_WIDTH = 400;
    public static final int WINDOW_HEIGHT = 300;
    JButton startGameButton;

    SettingsWindow(GameWindow gameWindow) {
        this.setLocationRelativeTo(gameWindow);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        startGameButton = new JButton("start new game");

        this.add(startGameButton);

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWindow.startNewGame(0, 3, 3, 3);//todo сделать настройки игры редактируемыми.
                setVisible(false);
            }
        });

    }

}
