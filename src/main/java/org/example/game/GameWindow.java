package org.example.game;

import lombok.NoArgsConstructor;
import org.example.game.gamePlay.players.Player;
import org.example.game.gamePlay.units.Unit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {
    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 720;
    public static final int WINDOW_POSX = 350;
    public static final int WINDOW_POSY = 200;
    Map map;
    SettingsWindow settingsWindow;


    JButton buttonStart = new JButton("New game");
    JButton buttonExit = new JButton("Exit");

    public GameWindow() throws HeadlessException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        JPanel panelBottom = new JPanel(new GridLayout(1, 2));

        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                settingsWindow.setVisible(true);
            }
        });
        panelBottom.add(buttonStart);
        panelBottom.add(buttonExit);
        add(panelBottom, BorderLayout.SOUTH);
        map = new Map();
        add(map);

        settingsWindow = new SettingsWindow(this);

        setResizable(false);
        setVisible(true);

    }

    public void startNewGame(java.util.Map<Player, Unit>playerUnitMap, int fieldSizeX, int fieldSizeY, int wLen) {
        map.startNewGame(playerUnitMap, fieldSizeX, fieldSizeY, wLen);
    }


}
