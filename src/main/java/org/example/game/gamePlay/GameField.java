package org.example.game.gamePlay;

import lombok.Data;
import org.example.game.gamePlay.players.Player;
import org.example.game.gamePlay.units.Unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * класс с логикой игры в крестики нолики
 */
@Data
public class GameField {
    private final int fieldSizeX;
    private final int fieldSizeY;
    private final int WinnerLineLength;
    /**
     * расположение фигур на игровом поле
     */
    private List<List<Unit>> gameField;
    /**
     * список игроков и их фигур
     */
    private Map<Player, Unit> players;
    /**
     * игрок который сейчас совершает ход
     */
    private Player currentPlayer;

    public GameField(int fieldSizeX, int fieldSizeY, int winnerLineLength) {
        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;
        WinnerLineLength = winnerLineLength;

        players = new HashMap<>();

        gameField = new ArrayList<>();
        for (int i = 0; i < fieldSizeY; i++) {
            gameField.add(new ArrayList<>());
            for (int j = 0; j < fieldSizeX; j++) {
                gameField.get(i).add(null);
            }
        }
    }

    /**
     * разместить фигуру unit на игровом поле gameField в указанной клетке
     *
     * @param unit         - фигура которую надо разместить
     * @param rowNumber    - строка, на которой находится проверяемая клетка
     * @param columnNumber - столбец, на которой находится проверяемая клетка
     * @return - в случае удачного размещения -> true / при возникновении ошибки todo
     */
    public boolean doTurn(Unit unit, int rowNumber, int columnNumber) {
        if (cellIsEmpty(rowNumber, columnNumber)) {
            gameField.get(rowNumber).set(columnNumber, unit);
            nextPlayer();
            return true;
        } else {
            return false;
        }
    }

    /**
     * проверят свободна ли клетка
     *
     * @param rowNumber    - строка, на которой находится проверяемая клетка
     * @param columnNumber - столбец, на которой находится проверяемая клетка
     * @return - если клетка пустая -> true / если занята -> false
     */
    private boolean cellIsEmpty(int rowNumber, int columnNumber) {
        return gameField.get(rowNumber).get(columnNumber) == null;
    }

    /**
     * найти победителя
     *
     * @return - фигуру побудителя (если победитель найден) в противном случае null
     */
    public List<Unit> winnerSearch() {
        // поиск по горизонтальным строкам
        for (List<Unit> units : gameField) {
            List<Unit> winnerLine = lineCheck(units);
            if (winnerLine != null) {
                return winnerLine;
            }
        }

        // поиск по вертикальным строкам
        for (int i = 0; i < gameField.get(0).size(); i++) {
            List<Unit> verticalLine = new ArrayList<>();
            for (int j = 0; j < gameField.size(); j++) {
                verticalLine.add(gameField.get(j).get(i));
            }
            List<Unit> winnerLine = lineCheck(verticalLine);

            if (winnerLine != null) {
                return winnerLine;
            }
        }

        // проверка диагонали 1
        int gameFieldRows = gameField.size();
        int gameFieldColumns = gameField.get(0).size();

        for (int i = 0; i < gameFieldColumns; i++) {
            List<Unit> diagonalLine = new ArrayList<>();
            int row = 0;
            int column;
            for (int j = i; j < gameFieldColumns; j++) {
                column = j;

                if (column < gameFieldColumns && row < gameFieldRows) {
                    diagonalLine.add(gameField.get(row).get(column));
                    row++;
                } else {
                    break;
                }
            }

            List<Unit> winnerLine = lineCheck(diagonalLine);
            if (winnerLine != null) {
                return winnerLine;
            }
        }
        //проверка под главной диагональю 1
        for (int i = 1; i < gameFieldRows; i++) {
            List<Unit> diagonalLine = new ArrayList<>();
            int row;
            int column = 0;
            for (int j = i; j < gameFieldRows; j++) {
                row = j;
                diagonalLine.add(gameField.get(row).get(column));
                column++;
            }
            List<Unit> winnerLine = lineCheck(diagonalLine);

            if (winnerLine != null) {
                return winnerLine;
            }


        }
        // проверка диагонали 2
        for (int i = gameFieldColumns - 1; i >= 0; i--) {
            List<Unit> diagonalLine = new ArrayList<>();
            int row = 0;
            int column;
            for (int j = i; j >= 0; j--) {
                column = j;

                if (column >= 0 && row < gameFieldRows) {
                    diagonalLine.add(gameField.get(row).get(column));
                    row++;
                } else {
                    break;
                }
            }

            List<Unit> winnerLine = lineCheck(diagonalLine);

            if (winnerLine != null) {
                return winnerLine;
            }
        }
        //проверка под главной диагональю 1
        for (int i = 1; i < gameFieldRows; i++) {
            List<Unit> diagonalLine = new ArrayList<>();
            int row;
            int column = gameFieldColumns - 1;
            for (int j = i; j < gameFieldRows; j++) {
                row = j;
                diagonalLine.add(gameField.get(row).get(column));
                column--;
            }
            List<Unit> winnerLine = lineCheck(diagonalLine);

            if (winnerLine != null) {
                return winnerLine;
            }


        }
        return null;
    }


    /**
     * поиск победителя в горизонтальной строке
     *
     * @param line - строка на поле в которой надо произвести поиск
     * @return - список победителей или null если победителей не нашлось
     */
    private List<Unit> lineCheck(List<Unit> line) {
        List<Unit> winners = new ArrayList<>();

        for (Unit currentUnit : line) {


            if (currentUnit == null) {
                winners = new ArrayList<>();
                continue;
            }

            if (winners.size() == 0) {
                winners.add(currentUnit);

            } else if (winners.get(0).getClass().isInstance(currentUnit)) {
                winners.add(currentUnit);
                if (winners.size() == WinnerLineLength) {
                    return winners;
                }
            } else {
                winners = new ArrayList<>();
                winners.add(currentUnit);
            }

        }
        return null;

    }


    /**
     * передача хода следующему пользователю
     */
    private void nextPlayer() {
        if (currentPlayer.getNumberInOrder() == players.size()) {
            currentPlayer = players.keySet().stream().filter((x) -> x.getNumberInOrder() == 1).toList().get(0);
        } else {
            currentPlayer = players.keySet().stream().filter((x) -> x.getNumberInOrder() == currentPlayer.getNumberInOrder() + 1).toList().get(0);
        }
    }

    /**
     * создание нового пользователя + присвоение ему номера в игре
     *
     * @param player - пользователь
     * @param unit   - фигура пользователя
     */
    public void addNewPlayer(Player player, Unit unit) {
        if (currentPlayer == null) {
            currentPlayer = player;
        }
        player.setNumberInOrder(players.size() + 1);
        players.put(player, unit);

    }

}
