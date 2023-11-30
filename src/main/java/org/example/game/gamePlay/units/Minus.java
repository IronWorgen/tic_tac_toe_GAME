package org.example.game.gamePlay.units;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.awt.*;


public class Minus extends Unit {
    public Minus() {
        super.setName("Минус");
    }

    public Minus(int positionOnFieldX, int positionOnFieldY) {
        super("Минус", positionOnFieldX, positionOnFieldY);
    }

    @Override
    public void print(Graphics graphics, int posX, int posY, int sizeX, int sizeY) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setStroke(new BasicStroke(5));

        graphics.drawLine(posX+sizeX/4,posY+sizeY/2, posX+sizeX*3/4, posY+sizeY/2);
        graphics2D.setStroke(new BasicStroke(1));
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
