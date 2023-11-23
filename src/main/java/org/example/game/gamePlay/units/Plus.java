package org.example.game.gamePlay.units;

import lombok.NoArgsConstructor;

import java.awt.*;
@NoArgsConstructor
public class Plus extends Unit{
    public Plus(int positionOnFieldX, int positionOnFieldY) {
        super(positionOnFieldX, positionOnFieldY);
    }

    @Override
    public void print(Graphics graphics, int posX, int posY, int sizeX, int sizeY) {


        graphics.drawLine(posX+sizeX/4,posY+sizeY/2, posX+sizeX*3/4, posY+sizeY/2);
        graphics.drawLine(posX+sizeX/2,posY+sizeY/4, posX+sizeX/2, posY+sizeY*3/4);
    }
}
