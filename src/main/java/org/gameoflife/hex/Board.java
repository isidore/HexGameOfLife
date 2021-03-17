package org.gameoflife.hex;

import java.util.List;

public class Board {
    private List<Cell> liveCells;

    public List<Cell> getLiveCells() {
        return liveCells;
    }

    public void setLiveCells(List<Cell> liveCells) {
        this.liveCells = liveCells;
    }

}
