package org.example.game.gamePlay.units;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.awt.*;


public class Plus extends Unit {
    public Plus() {
        super.setName("Плюсик");
    }

    public Plus(int positionOnFieldX, int positionOnFieldY) {
        super("Плюсик",positionOnFieldX, positionOnFieldY);
    }

    @Override
    public void print(Graphics graphics, int posX, int posY, int sizeX, int sizeY) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setStroke(new BasicStroke(5));


        graphics2D.drawLine(posX + sizeX / 4, posY + sizeY / 2, posX + sizeX * 3 / 4, posY + sizeY / 2);
        graphics2D.drawLine(posX + sizeX / 2, posY + sizeY / 4, posX + sizeX / 2, posY + sizeY * 3 / 4);
        graphics2D.setStroke(new BasicStroke(1));
    }

    @Override
    public String toString() {
        return "Plus{" + super.getPositionOnFieldX() + "\t" + super.getPositionOnFieldY() + "}";
    }

    /**
     * клонирует этот класс
     *
     * @return новый экземпляр с теми же полями
     */
    @Override
    public Plus clone() {
        return new Plus(super.getPositionOnFieldX(), super.getPositionOnFieldY());

    }
}
