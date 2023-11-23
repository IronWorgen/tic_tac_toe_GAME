package org.example.game.gamePlay.units;

import lombok.NoArgsConstructor;

import java.awt.*;
@NoArgsConstructor
public class Red extends Unit{
    public Red(int positionOnFieldX, int positionOnFieldY) {
        super(positionOnFieldX, positionOnFieldY);
    }

    @Override
    public void print(Graphics graphics, int posX, int posY, int sizeX, int sizeY) {

    }
}
