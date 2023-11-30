package org.example.game;

import org.example.game.gamePlay.players.Human;
import org.example.game.gamePlay.players.Player;
import org.example.game.gamePlay.units.Minus;
import org.example.game.gamePlay.units.Plus;
import org.example.game.gamePlay.units.Unit;
import org.example.game.services.PlayerService;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingsWindow extends JFrame {
    public static final int WINDOW_WIDTH = 400;
    public static final int WINDOW_HEIGHT = 300;
    private JButton startGameButton;
    private JButton addPlayer;

    private JPanel gameFieldSettings;
    JLabel gameField = new JLabel("Установленный размер поля:");
    JSlider fieldSlider = new JSlider(3,10);
    JSlider winLengthSlider = new JSlider(3,10);

    private  Map<Player, Unit> playerUnitMap ;

    private CreatePlayerWindow createPlayerWindow;
    private PlayerService playerService;

    SettingsWindow(GameWindow gameWindow) {
        playerService = new PlayerService();
        playerUnitMap = playerService.getPlayersAndUnits();
        createPlayerWindow= new CreatePlayerWindow(this, playerService);
        this.setLocationRelativeTo(gameWindow);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        showSettings(gameWindow);


        addPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo добавить добавление пользователя по кнопке


                createPlayerWindow.setVisible(true);
                playerUnitMap = playerService.getPlayersAndUnits();


                showSettings(gameWindow);
            }
        });

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWindow.startNewGame( playerUnitMap,3, 3, 3);//todo сделать настройки игры редактируемыми.
                setVisible(false);
            }
        });
        this.add(gameFieldSettings);


    }

    private void showSettings(GameWindow gameWindow){

        gameFieldSettings= new JPanel(new GridLayout(9,1));
        gameFieldSettings.add(new JLabel("Параметры игрового поля"));
        gameFieldSettings.add(gameField);

        fieldSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                gameField.setText("Установленный размер поля: " + fieldSlider.getValue());
            }
        });
        gameFieldSettings.add(fieldSlider);

        startGameButton = new JButton("start new game");
        gameFieldSettings.add(startGameButton );
        addPlayer= new JButton("Добавить игрока");
        gameFieldSettings.add(addPlayer);




    }

    private DefaultListModel<String> createPlayersList( ){
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Map.Entry<Player, Unit> playerUnitEntry : playerUnitMap.entrySet()) {
            model.addElement(String.format("%s - %s", playerUnitEntry.getKey().getName(), playerUnitEntry.getValue().getName()));
        }
        return model;
    }


}
