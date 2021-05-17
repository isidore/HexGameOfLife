package org.gameoflife.hex.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cell {
    private final int x;
    private final int y;

    public Cell(int x, int y) {

        this.x = x;
        this.y = y;
    }

    public static Cell create(int x, int y) {
        return new Cell(x, y);
    }

    public List<Cell> getAllNeighbours() {
        ArrayList<Cell> neighbours = new ArrayList<>();
        neighbours.addAll(getLevelOneNeighbours());
        neighbours.addAll(getLevelTwoNeighbours());
        return neighbours;
    }

    public List<Cell> getLevelOneNeighbours() {
        List<Cell> cells = new ArrayList<>();
        cells.add(new Cell(x - 1, y - 1));
        cells.add(new Cell(x + 1, y - 1));
        cells.add(new Cell(x - 2, y));
        cells.add(new Cell(x + 2, y));
        cells.add(new Cell(x - 1, y + 1));
        cells.add(new Cell(x + 1, y + 1));

        return cells;
    }

    public List<Cell> getLevelTwoNeighbours() {
        List<Cell> cells = new ArrayList<>();
        cells.add(new Cell(x, y - 2));
        cells.add(new Cell(x + 3, y - 1));
        cells.add(new Cell(x - 3, y - 1));
        cells.add(new Cell(x + 3, y + 1));
        cells.add(new Cell(x - 3, y + 1));
        cells.add(new Cell(x, y + 2));

        return cells;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
