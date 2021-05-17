package org.gameoflife.hex.game;


import org.gameoflife.hex.Cell;

import java.util.List;

public class Board {
    private List<Cell> liveCells;

    List<Cell> getLiveCells() {
        return liveCells;
    }

    void setLiveCells(List<Cell> liveCells) {
        this.liveCells = liveCells;
    }

    void setAlive(int x, int y) {
        liveCells.add(new Cell(x, y));
    }

    boolean isAlive(Cell cell) {
        return liveCells.contains(cell);
    }

}
