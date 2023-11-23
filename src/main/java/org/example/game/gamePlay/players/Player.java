package org.example.game.gamePlay.players;

import lombok.Data;

@Data
public class Player {
    /**
     * очередь хода пользователя
     */
    private int numberInOrder;
    /**
     * имя пользователя
     */
    private final String name;

    public Player( String name) {
        this.name = name;
    }
}

