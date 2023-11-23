package org.example.game.gamePlay.units;

import lombok.NoArgsConstructor;

import java.awt.*;
@NoArgsConstructor
public class Minus extends Unit{
    public Minus(int positionOnFieldX, int positionOnFieldY) {
        super(positionOnFieldX, positionOnFieldY);
    }

    @Override
    public void print(Graphics graphics, int posX, int posY, int sizeX, int sizeY) {


        graphics.drawLine(posX+sizeX/4,posY+sizeY/2, posX+sizeX*3/4, posY+sizeY/2);
    }
}
