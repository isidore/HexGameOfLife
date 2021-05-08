package org.gameoflife.hex;


import com.spun.util.FormattedException;

import java.util.List;

public class Board {
    private List<Cell> liveCells;

    static boolean isValidCoordinate(Coordinates gridAt) {
        boolean xIsEven = gridAt.getX() % 2 == 0;
        boolean yIsEven = gridAt.getY() % 2 == 0;

        return yIsEven == xIsEven;
    }

    public List<Cell> getLiveCells() {
        return liveCells;
    }

    public void setLiveCells(List<Cell> liveCells) {
        this.liveCells = liveCells;
    }

    void setAlive(int x, int y) {
        if (!GameOfLifeInterface.isValidCoordinates(new Coordinates(x, y))) {
            throw new FormattedException("Invalid Location for (%s, %s)", x, y);
        }

        liveCells.add(new Cell(x, y));
    }

    boolean isAlive(Cell cell) {
        return liveCells.contains(cell);
    }

    @Override
    public String toString() {
        return HexPrinter.print(this);
    }

}
