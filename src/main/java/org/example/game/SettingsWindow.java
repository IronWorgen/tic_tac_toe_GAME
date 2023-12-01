package org.example.game;

import lombok.Data;
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

@Data
public class SettingsWindow extends JFrame {
    public static final int WINDOW_WIDTH = 400;
    public static final int WINDOW_HEIGHT = 400;
    private JButton startGameButton;
    private JButton addPlayer;

    private JPanel gameFieldSettings;
    private JLabel gameFieldX = new JLabel("Количество столбцов: 3");
    private JLabel gameFieldY = new JLabel("Количество строк: 3");
    private JLabel gameWinLengthLabel = new JLabel("Длина динии для победы: 3");
    private JSlider gameWinLengthSlider = new JSlider(3, 10);
    private JSlider fieldSliderX = new JSlider(3, 10);
    private JSlider fieldSliderY = new JSlider(3, 10);
    private JSlider winLengthSlider = new JSlider(3, 10);
    private JLabel playersCounter = new JLabel("Количество игроков = 0");

    private Map<Player, Unit> playerUnitMap;

    private CreatePlayerWindow createPlayerWindow;
    private PlayerService playerService;

    SettingsWindow(GameWindow gameWindow) {
        playerService = new PlayerService();
        playerUnitMap = playerService.getPlayersAndUnits();
        createPlayerWindow = new CreatePlayerWindow(this, playerService);
        this.setLocationRelativeTo(gameWindow);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        showSettings();

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWindow.startNewGame(playerUnitMap,
                        fieldSliderX.getValue(),
                        fieldSliderY.getValue(),
                        winLengthSlider.getValue());
                setVisible(false);
            }
        });

        addPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                createPlayerWindow.setVisible(true);
                playerUnitMap = playerService.getPlayersAndUnits();

            }
        });

        this.add(gameFieldSettings);
    }

    /**
     * расположение компонентов на окне настроек
     */
    private void showSettings() {

        gameFieldSettings = new JPanel(new GridLayout(12, 1));
        gameFieldSettings.add(new JLabel("Параметры игрового поля"));

        gameFieldSettings.add(gameFieldY);
        fieldSliderY.setValue(3);
        gameFieldSettings.add(fieldSliderY);
        fieldSliderY.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                gameFieldY.setText("Количество строк: " + fieldSliderY.getValue());
            }
        });

        gameFieldSettings.add(gameFieldX);
        gameFieldSettings.add(fieldSliderX);
        fieldSliderX.setValue(3);
        fieldSliderX.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                gameFieldX.setText("Количество столбцов: " + fieldSliderX.getValue());
            }
        });

        gameFieldSettings.add(gameWinLengthLabel);
        gameFieldSettings.add(winLengthSlider);
        winLengthSlider.setValue(3);

        winLengthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                gameWinLengthLabel.setText("Длина динии для победы: " + winLengthSlider.getValue());
            }
        });


        addPlayer = new JButton("Добавить игрока");
        gameFieldSettings.add(addPlayer);
        gameFieldSettings.add(playersCounter);

        JButton deleteAllPlayers = new JButton("очистить список игроков");
        deleteAllPlayers.addActionListener((e) -> {
            playerService.removeAll();
            updatePlayersList();
            startGameButton.setEnabled(false);
            playersCounter.setText("Количество игроков = 0");

        });
        gameFieldSettings.add(deleteAllPlayers);

        gameFieldSettings.add(new JLabel());
        startGameButton = new JButton("start new game");
        gameFieldSettings.add(startGameButton);
        if (playerUnitMap.size() == 0) {
            startGameButton.setEnabled(false);
        }
    }

    /**
     * обновить список игроков.
     */
    public void updatePlayersList() {
        playerUnitMap = playerService.getPlayersAndUnits();
        playersCounter.setText("Количество игроков = " + playerUnitMap.size());
        startGameButton.setEnabled(true);

    }
}
