package org.gameoflife.hex;

import org.gameoflife.hex.game.Cell;
import org.gameoflife.hex.game.HexGameOfLife;

public interface GameOfLife {

    static boolean isValidCoordinates(Coordinates coordinates) {
        boolean xIsEven = coordinates.getX() % 2 == 0;
        boolean yIsEven = coordinates.getY() % 2 == 0;

        return yIsEven == xIsEven;
    }

    HexGameOfLife advanceTurn();

    void setAlive(int x, int y);

    @Override
    String toString();

    boolean isAlive(Cell cell);
}
