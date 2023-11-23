package org.example.game.gamePlay;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.game.gamePlay.players.Player;
import org.example.game.gamePlay.units.Unit;

import java.util.ArrayList;
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

    public GameField(int fieldSizeX, int fieldSizeY, int winnerLineLength) {
        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;
        WinnerLineLength = winnerLineLength;

        gameField= new ArrayList<>();
        for (int i = 0; i < fieldSizeY; i++) {
            gameField.add(new ArrayList<>());
            for (int j = 0; j < fieldSizeX; j++) {
                gameField.get(i).add(null);
            }
        }
    }

    /**
     * разместить фигуру unit на игровом поле gameField в указанной клетке
     * @param unit - фигура которую надо разместить
     * @param rowNumber - строка, на которой находится проверяемая клетка
     * @param columnNumber - столбец, на которой находится проверяемая клетка
     * @return - в случае удачного размещения -> true / при возникновении ошибки todo
     */
    public boolean doTurn(Unit unit, int rowNumber, int columnNumber){
        if(cellIsEmpty(rowNumber, columnNumber)){
            gameField.get(rowNumber).set(columnNumber, unit);
            return true;
        }else {
            return false;
        }
    }

    /**
     * проверят свободна ли клетка
     * @param rowNumber - строка, на которой находится проверяемая клетка
     * @param columnNumber - столбец, на которой находится проверяемая клетка
     * @return - если клетка пустая -> true / если занята -> false
     */
    private boolean cellIsEmpty(int rowNumber, int columnNumber){
        return gameField.get(rowNumber).get(columnNumber)==null;
    }

    /**
     * найти победителя
     * @return - фигуру побудителя (если победитель найден) в противном случае null
     */
    public Unit winnerSearch(){
        return null;//todo сделать логику поиска победителя игры
    }
}
