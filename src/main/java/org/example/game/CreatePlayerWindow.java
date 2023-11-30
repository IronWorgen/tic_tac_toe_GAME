package org.example.game;

import org.example.game.gamePlay.players.Player;
import org.example.game.gamePlay.units.Minus;
import org.example.game.gamePlay.units.Plus;
import org.example.game.gamePlay.units.Unit;
import org.example.game.services.PlayerService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreatePlayerWindow extends JFrame {
    PlayerService playerService;
    private  static final int WINDOW_WIDTH = 400;
    private  static final int WINDOW_HEIGHT = 300;

    private SettingsWindow settingsWindow;
    private JComboBox<String> selectUnitComboBox;
    private JTextField playerNameTextField;
    JButton confirmButton = new JButton("Подтвердить");
    private List<Unit> unitTypes ;

    public CreatePlayerWindow(SettingsWindow settingsWindow,PlayerService playerService) throws HeadlessException {
        this.playerService = playerService;
        unitTypes=new ArrayList<>(Arrays.asList(new Unit[]{new Plus(), new Minus()}));
        this.settingsWindow = settingsWindow;
        this.setLocationRelativeTo(settingsWindow);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        JPanel mainPanel = new JPanel(new GridLayout(5,1));

        JPanel playerName = new JPanel(new FlowLayout());
        playerName.add(new JLabel("Введите ваше имя"));
        playerNameTextField = new JTextField();
        playerNameTextField.setColumns(10);
        playerName.add(playerNameTextField);
        mainPanel.add(playerName);

        JPanel unitSelect = new JPanel(new FlowLayout());
        unitSelect.add(new JLabel("Выберите фигурку: "));

        String[] units = new String[unitTypes.size()];
        for (int i = 0; i < units.length; i++) {
            units[i]=unitTypes.get(i).getName();
        }
        selectUnitComboBox =  new JComboBox<>(units);
        unitSelect.add(selectUnitComboBox);
        mainPanel.add(unitSelect);

        confirmButton.addActionListener((event)->{
            this.setVisible(false);
            playerService.addPlayer(new Player(playerNameTextField.getText()), createUnitFromComboBox());

        });
        mainPanel.add(confirmButton);

        this.add(mainPanel);
    }

    private Unit createUnitFromComboBox(){
        if(selectUnitComboBox.getSelectedItem().equals("Плюсик")){
            return new Plus();
        } else if (selectUnitComboBox.getSelectedItem().equals("Минус")) {
            return new Minus();
        }
        return null;
    }
}
