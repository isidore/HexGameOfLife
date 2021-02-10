package org.gameoflife.hex;

import com.spun.util.FormattedException;
import org.lambda.utils.Grid;

import java.util.*;

public class GameOfLifeBoard {
    private List<Cell> cells;

    public GameOfLifeBoard(Cell[] cells) {

        this.cells = Arrays.asList(cells);
    }

    public GameOfLifeBoard() {
        cells = new ArrayList<>();
    }

    public GameOfLifeBoard(List<Cell> cells) {

        this.cells = cells;
    }

    private String printCell(Integer x, Integer y) {
        if (Cell.isValid(x, y)) {
            if (cells.contains(new Cell(x, y))) {
                return "X";
            }
            return "_";
        }
        return " ";
    }

    @Override
    public String toString() {
        return Grid.print(10, 10, this::printCell);
    }

    public void setAlive(int x, int y) {
        if (!Cell.isValid(x, y)) {
            throw new FormattedException("Invalid Location for (%s, %s)", x, y);
        }

        cells.add(new Cell(x, y));
    }

    public GameOfLifeBoard advanceTurn() {
        ArrayList<Cell> next = new ArrayList<>();
        //iterate over all cells on the board
        for (Cell cell : getRelevantCells()) {
            double sum = 0;
            for (Cell level1 : cell.getLevelOneNeighbours()) {
                if (isAlive(level1)) {
                    sum++;
                }
            }
            if (2 <= sum && sum <= 3.3 && isAlive(cell)){
                next.add(cell);
            }
        }
        //get level 1 neighbours
        //sum up the ones that are alive
        //add if appropriate
        return new GameOfLifeBoard(next);
    }

    private boolean isAlive(Cell cell) {
        return cells.contains(cell);
    }

    private Set<Cell> getRelevantCells() {
        HashSet<Cell> cells2 = new HashSet<>();
        cells2.addAll(cells);
        for (Cell cell : cells) {
            cells2.addAll(cell.getLevelOneNeighbours());
            cells2.addAll(cell.getLevelTwoNeighbours());
        }
        return cells2;
    }
}
