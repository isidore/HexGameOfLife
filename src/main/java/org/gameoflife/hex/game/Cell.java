package org.gameoflife.hex.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cell {
    private final int x;
    private final int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Cell(int x, int y) {

        this.x = x;
        this.y = y;
    }

    List<Cell> getAllNeighbours() {
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

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
