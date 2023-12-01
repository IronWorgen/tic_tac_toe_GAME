package org.example.game;

import lombok.Data;
import org.example.game.gamePlay.GameField;
import org.example.game.gamePlay.players.Human;
import org.example.game.gamePlay.players.Player;
import org.example.game.gamePlay.units.Minus;
import org.example.game.gamePlay.units.Plus;
import org.example.game.gamePlay.units.Unit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class Map extends JPanel {
    private int panelWidth;
    private int panelHeight;

    private int cellWidth;
    private int cellHeight;
    /**
     * размещение фигур на игровом поле + логика игры
     */
    GameField gameField;
    /**
     * линия победителя
     */
    List<Unit> winnersList;

    Map() {
        this.gameField = new GameField(3, 3, 3);
    }

    /**
     * начинает новую игру
     * @param playerUnitMap - список игроков участвующих в следующей игре.
     * @param fieldSizeX - количество столбцов игрового поля
     * @param fieldSizeY - количество строк игрового поля
     * @param wLen - длина линии, для победы
     */
    void startNewGame(java.util.Map<Player, Unit> playerUnitMap, int fieldSizeX, int fieldSizeY, int wLen) {
        gameField = new GameField(fieldSizeX, fieldSizeY, wLen);
        winnersList = null;//обнуление победителей
        for (java.util.Map.Entry<Player, Unit> playerUnitEntry : playerUnitMap.entrySet()) {
            gameField.addNewPlayer(playerUnitEntry.getKey(), playerUnitEntry.getValue());
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                update(e);
            }
        });
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    /**
     * отрисовка игрового поля
     */
    private void render(Graphics graphics) {
        graphics.setColor(Color.BLACK);

        panelWidth = getWidth();
        panelHeight = getHeight();
        cellHeight = panelHeight / gameField.getFieldSizeY();
        cellWidth = panelWidth / gameField.getFieldSizeX();

        List<List<Unit>> currentGameField = gameField.getGameField();
        for (int i = 0; i < gameField.getFieldSizeY(); i++) {
            for (int j = 0; j < gameField.getFieldSizeX(); j++) {
                Unit unitInCurrentCell = currentGameField.get(i).get(j);
                if (unitInCurrentCell != null) {
                    unitInCurrentCell.print(graphics,
                            j * cellWidth,
                            i * cellHeight,
                            cellWidth,
                            cellHeight);
                }
            }
        }

        for (int i = 1; i < gameField.getFieldSizeY(); i++) {
            graphics.drawLine(0, i * cellHeight, panelWidth, i * cellHeight);
        }
        for (int i = 1; i < gameField.getFieldSizeX(); i++) {
            graphics.drawLine(i * cellWidth, 0, i * cellWidth, panelWidth);
        }
        if (winnersList != null) {
            showWinners(graphics);

        }


    }

    /**
     * отрисовывает победителя игры
     *
     * @param graphics
     */
    private void showWinners(Graphics graphics) {
        graphics.setColor(Color.red);
        Unit firstUnitFromWinnersList = winnersList.get(0);
        int previousCellCenterX = firstUnitFromWinnersList.getPositionOnFieldX() * cellWidth + cellWidth / 2;
        int previousCellCenterY = firstUnitFromWinnersList.getPositionOnFieldY() * cellHeight + cellHeight / 2;
        for (int i = 1; i < winnersList.size(); i++) {
            int currentCellCenterX = winnersList.get(i).getPositionOnFieldX() * cellWidth + cellWidth / 2;
            int currentCellCenterY = winnersList.get(i).getPositionOnFieldY() * cellHeight + cellHeight / 2;
            graphics.drawLine(previousCellCenterX, previousCellCenterY, currentCellCenterX, currentCellCenterY);
        }
        graphics.setColor(Color.black);
        JFrame winner = new JFrame();
        winner.setSize(300, 200);
        winner.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        String winnerMessage = String.format("Победитель:\t %s", gameField.getCurrentPlayer().getName());
        JLabel winnerLabel = new JLabel(winnerMessage);
        winnerLabel.setFont(new Font("TimesRoman", Font.BOLD, 22));
        panel.add(winnerLabel);
        winner.add(panel, BorderLayout.CENTER);
        winner.setVisible(true);

    }



    /**
     * при нажатии на кнопку мыши пользователь совершает ход
     * выполняется проверка не занята ли ячейка
     * в случае, если ячейка не занята, прерисовывает игровое поле
     *
     * @param mouseEvent
     */
    private void update(MouseEvent mouseEvent) {

        int cellX = mouseEvent.getX() / cellWidth;
        int cellY = mouseEvent.getY() / cellHeight;

        Unit testunit = gameField.getPlayers().get(gameField.getCurrentPlayer());
        Unit currentUnit = testunit.clone();
        currentUnit.setPositionOnFieldX(cellX);
        currentUnit.setPositionOnFieldY(cellY);
        if (gameField.doTurn(currentUnit, cellY, cellX)) {
            winnersList = gameField.winnerSearch();

            repaint();
            if (winnersList != null) {
                this.removeMouseListener(this.getMouseListeners()[0]);
            } else {
                gameField.nextPlayer();
            }


        } else {
            JOptionPane.showMessageDialog(new JFrame(),"Ячейка занята");
        }

        repaint();

    }
}
