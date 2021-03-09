package org.gameoflife.hex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Cell {
    private int x;
    private int y;

    public Cell(int x, int y) {

        this.x = x;
        this.y = y;
    }

    public static Cell create(int x, int y) {
        return new Cell(x,y);
    }

    public static List<Cell> getLevelOneNeighbours(int x, int y) {
        List<Cell> cells = new ArrayList<>();
        cells.add(new Cell(x - 1, y-1));
        cells.add(new Cell(x - 1, y+1));
        cells.add(new Cell(x , y-2));
        cells.add(new Cell(x , y+2));
        cells.add(new Cell(x +1, y-1));
        cells.add(new Cell(x + 1, y+1));

        return cells;
    }

    public static List<Cell> getLevelTwoNeighbours(int x, int y) {
        List<Cell> cells = new ArrayList<>();
        cells.add(new Cell(x-2, y));
        cells.add(new Cell(x -1, y+3));
        cells.add(new Cell(x - 1, y-3));
        cells.add(new Cell(x + 1, y+3));
        cells.add(new Cell(x +1, y-3));
        cells.add(new Cell(x+2 , y));

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

    public List<Cell> getLevelOneNeighbours() {
        return getLevelOneNeighbours(x,y);
    }

    public List<Cell> getLevelTwoNeighbours() {
        return getLevelTwoNeighbours(x, y);
    }
}
