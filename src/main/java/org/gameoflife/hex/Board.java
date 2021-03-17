package org.gameoflife.hex;

import java.util.List;

public class Board {
    private List<Cell> liveCells;

    public static boolean isValidCoordinate(int x, int y) {
        boolean xIsEven = x % 2 == 0;
        boolean yIsEven = y % 2 == 0;

        return yIsEven == xIsEven;
    }

    public List<Cell> getLiveCells() {
        return liveCells;
    }

    public void setLiveCells(List<Cell> liveCells) {
        this.liveCells = liveCells;
    }

}
