package org.example.game.services;

import lombok.NoArgsConstructor;

import org.example.game.exceptions.WrongPlayerNameException;
import org.example.game.gamePlay.players.Player;
import org.example.game.gamePlay.units.Unit;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class PlayerService {
    Map<Player, Unit> playerUnitMap = new HashMap<>();

    /**
     * получить список игроков и их фигурок
     * @return Map<Player, Unit>
     */
    public Map<Player, Unit> getPlayersAndUnits(){
        return this.playerUnitMap;
    }

    /**
     * сохранить игрока и фигуру
     * @param player -игрок
     * @param unit - фигура
     */
    public void  addPlayer(Player player, Unit unit) throws WrongPlayerNameException{
        if (!nameIsUnique(player.getName())){
            throw new WrongPlayerNameException(player.getName());
        }
        playerUnitMap.put(player, unit);

    }

    /**
     * проверка есть ли игрок с таким именем
     * @param name
     * @return true -> уникальное имя, false -> уже сеть игрок с таким именем
     */
    private boolean nameIsUnique(String name){
        for (Player player : playerUnitMap.keySet()) {
            if(player.getName().equals(name)){
                return false;
            }
        }
        return true;
    }

    /**
     * удалить всех игроков
     */
    public void  removeAll(){
        playerUnitMap = new HashMap<>();
    }
}
