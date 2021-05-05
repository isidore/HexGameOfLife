package org.gameoflife.hex;


import com.spun.util.FormattedException;

import java.util.List;

public class Board {
    private List<Cell> liveCells;

    public static boolean isValidCoordinate2(Coordinates gridAt) {
        return isValidCoordinate(gridAt.getX(), gridAt.getY());
    }

    public List<Cell> getLiveCells() {
        return liveCells;
    }

    public void setLiveCells(List<Cell> liveCells) {
        this.liveCells = liveCells;
    }

    void setAlive(int x, int y) {
        if (!isValidCoordinate(x, y)) {
            throw new FormattedException("Invalid Location for (%s, %s)", x, y);
        }

        liveCells.add(new Cell(x, y));
    }

    public static boolean isValidCoordinate(int x, int y) {
        boolean xIsEven = x % 2 == 0;
        boolean yIsEven = y % 2 == 0;

        return yIsEven == xIsEven;
    }

    public boolean isAlive(Cell cell) {
        return liveCells.contains(cell);
    }

    @Override
    public String toString() {
        return HexPrinter.print(this);
    }

    public boolean isAlive(int x, int y) {
        return isAlive(new Cell(x, y));
    }
}
