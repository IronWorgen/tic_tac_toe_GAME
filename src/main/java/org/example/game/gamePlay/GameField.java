package org.example.game.gamePlay;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.game.gamePlay.players.Player;
import org.example.game.gamePlay.units.Unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            nextPlayer();
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
    public List<Unit> winnerSearch(){
        for (List<Unit> units : gameField) {
            List<Unit> winnerLine = horizontalLineCheck(units);
            if (winnerLine!=null){
                return winnerLine;
            }
        }
        return null;
    }

    /**
     * поиск победителя в горизонтальной строке
     * @param line - строка на поле в которой надо произвести поиск
     * @return - список победителей или null если победителей не нашлось
     */
    private List<Unit> horizontalLineCheck(List<Unit> line){
        List<Unit> winners = new ArrayList<>();

        for (Unit currentUnit : line) {

            if (currentUnit == null){
                winners = new ArrayList<>();
                continue;
            }

            if (winners.size()==0){
                winners.add(currentUnit);

            }else if (winners.get(0).getClass().isInstance(currentUnit)){
                winners.add(currentUnit);
                if (winners.size()==WinnerLineLength){
                    return winners;
                }
            }else{
                winners =  new ArrayList<>();
                winners.add(currentUnit);
            }
        }
        return null;
    }


    /**
     * передача хода следующему пользователю
     */
    private void nextPlayer(){
        if (currentPlayer.getNumberInOrder()==players.size()){
            currentPlayer= players.keySet().stream().filter((x)->x.getNumberInOrder()==1).toList().get(0);
        }else{
            currentPlayer= players.keySet().stream().filter((x)->x.getNumberInOrder()==currentPlayer.getNumberInOrder()+1).toList().get(0);
        }
    }

    /**
     * создание нового пользователя + присвоение ему номера в игре
     * @param player - пользователь
     * @param unit - фигура пользователя
     */
    public void addNewPlayer(Player player, Unit unit){
        if (currentPlayer==null){
            currentPlayer=player;
        }
        player.setNumberInOrder(players.size()+1);
        players.put(player, unit);

    }

}
