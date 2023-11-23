package org.example.game.gamePlay.units;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.awt.*;
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Unit {

    private int positionOnFieldX ;
    private int positionOnFieldY;

    /**
     * отрисовывает фигуру в переданных координатах
     * @param graphics
     * @param posX - координата X верхнего левого угла ячейки
     * @param posY - координата Y верхнего левого угла ячейки
     * @param sizeX - ширина ячейки
     * @param sizeY - высота ячейки
     */
    public abstract void print(Graphics graphics, int posX, int posY, int sizeX, int sizeY);
}
