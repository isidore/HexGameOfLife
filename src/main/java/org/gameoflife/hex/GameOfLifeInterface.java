package org.gameoflife.hex;

import org.apache.commons.lang.math.DoubleRange;

public interface GameOfLifeInterface {
    static boolean survivesToNextTurn(double sum, boolean alive) {
        DoubleRange survivable = new DoubleRange(2, 3.3);
        DoubleRange growth = new DoubleRange(2.3, 2.9);

        boolean survives = survivable.containsDouble(sum) && alive;
        boolean born = growth.containsDouble(sum);
        return survives || born;
    }

    static boolean isValidCoordinates(Coordinates coordinates) {
        return Board.isValidCoordinate(coordinates);
    }

    GameOfLife advanceTurn();

    void setAlive(int x, int y);

    @Override
    String toString();

    boolean isAlive(Cell cell);
}
