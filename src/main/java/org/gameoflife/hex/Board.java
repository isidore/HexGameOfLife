package org.gameoflife.hex;

import com.spun.util.FormattedException;

import java.util.List;

public class Board {
    private List<Cell> liveCells;


    public List<Cell> getLiveCells() {
        return liveCells;
    }

    public void setLiveCells(List<Cell> liveCells) {
        this.liveCells = liveCells;
    }

    public void setAlive(int x, int y) {
        if (!isValidCoordinate(x, y)) {
            throw new FormattedException("Invalid Location for (%s, %s)", x, y);
        }

        getLiveCells().add(new Cell(x, y));
    }

    public static boolean isValidCoordinate(int x, int y) {
        boolean xIsEven = x % 2 == 0;
        boolean yIsEven = y % 2 == 0;

        return yIsEven == xIsEven;
    }

    public boolean isAlive(Cell cell) {
        return getLiveCells().contains(cell);
    }

    @Override
    public String toString() {
        return HexPrinter.print(this);
    }
}
