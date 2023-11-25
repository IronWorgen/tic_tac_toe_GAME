package org.example.game.gamePlay.units;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.awt.*;
@NoArgsConstructor

public class Minus extends Unit {
    public Minus(int positionOnFieldX, int positionOnFieldY) {
        super(positionOnFieldX, positionOnFieldY);
    }

    @Override
    public void print(Graphics graphics, int posX, int posY, int sizeX, int sizeY) {


        graphics.drawLine(posX+sizeX/4,posY+sizeY/2, posX+sizeX*3/4, posY+sizeY/2);
    }

    @Override
    public String toString() {
        return "Minus{"+super.getPositionOnFieldX()+"\t"+super.getPositionOnFieldY()+"}";
    }

    /**
     * клонирует этот класс
     * @return новый экземпляр с теми же полями
     */
    @Override
    public Minus clone() {
        return new Minus(super.getPositionOnFieldX(), super.getPositionOnFieldY());

    }
}
