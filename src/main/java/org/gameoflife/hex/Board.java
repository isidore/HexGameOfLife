package org.gameoflife.hex;


import com.spun.util.FormattedException;

import java.util.List;

public class Board {
    public List<Cell> liveCells;

    public List<Cell> getLiveCells() {
        return liveCells;
    }

    public void setLiveCells(List<Cell> liveCells) {
        this.liveCells = liveCells;
    }

    public void setAlive(int x, int y) {
        if (!GameOfLife.isValidCoordinates(new Coordinates(x, y))) {
            throw new FormattedException("Invalid Location for (%s, %s)", x, y);
        }

        liveCells.add(new Cell(x, y));
    }

    public boolean isAlive(Cell cell) {
        return liveCells.contains(cell);
    }

}
