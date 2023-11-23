package org.example.game;

import lombok.Data;
import org.example.game.gamePlay.GameField;
import org.example.game.gamePlay.players.Human;
import org.example.game.gamePlay.units.Minus;
import org.example.game.gamePlay.units.Plus;
import org.example.game.gamePlay.units.Unit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    Map() {
        this.gameField = new GameField(4, 3, 3);

    }


    void startNewGame(int mode, int fieldSizeX, int fieldSizeY, int wLen) {
        gameField = new GameField(fieldSizeX, fieldSizeY, wLen); //todo сделать логику игры против бота
        gameField.addNewPlayer(new Human("ivan"),new Plus());
        gameField.addNewPlayer(new Human("daniil"),new Minus());

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

    }

    /**
     * метод начала игры
     */
    private void gameConfig() {//todo при необходимости удалить
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                update(e);

            }
        });
    }

    private void update(MouseEvent mouseEvent) {

        int cellX = mouseEvent.getX() / cellWidth;
        int cellY = mouseEvent.getY() / cellHeight;

        Unit currentUnit = gameField.getPlayers().get(gameField.getCurrentPlayer());
        if (gameField.doTurn(currentUnit, cellY, cellX)) {
            repaint();
            System.out.println("удачный ход");//todo добавить логику в случае если ячейка свободна
        } else {
            System.out.println("клетка занята");//todo добавить логику в случае если ячейка занята
        }


        System.out.printf("x=%d,\ty=%d\n", cellX, cellY);
        repaint();

    }


}
