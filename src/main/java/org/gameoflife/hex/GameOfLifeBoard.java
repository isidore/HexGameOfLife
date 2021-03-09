package org.gameoflife.hex;

import com.spun.util.FormattedException;
import org.lambda.utils.Grid;

import java.util.*;

public class GameOfLifeBoard {
    private List<Cell> cells;

    public GameOfLifeBoard() {
        cells = new ArrayList<>();
    }

    public GameOfLifeBoard(List<Cell> cells) {

        this.cells = cells;
    }

    public static boolean isValidCoordinate(int x, int y) {
        boolean xIsEven = x%2 == 0;
        boolean yIsEven = y%2 == 0;

        return yIsEven == xIsEven;
    }

    public void setAlive(int x, int y) {
        if (!isValidCoordinate(x, y)) {
            throw new FormattedException("Invalid Location for (%s, %s)", x, y);
        }

        cells.add(new Cell(x, y));
    }

    @Override
    public String toString() {
        return HexPrinter.print(this);
    }


    public GameOfLifeBoard advanceTurn() {
        ArrayList<Cell> nextLivingCells = new ArrayList<>();
        for (Cell cell : getRelevantCells()) {
            if (isAliveNextTurn(getNeighbourScore(cell), isAlive(cell))) {
                nextLivingCells.add(cell);
            }
        }
        return new GameOfLifeBoard(nextLivingCells);
    }

    private static boolean isAliveNextTurn(double sum, boolean alive) {
        return 2 <= sum && sum <= 3.3 && alive;
    }

    private double getNeighbourScore(Cell cell) {
        return getScore(cell.getLevelOneNeighbours(), 1) + getScore(cell.getLevelTwoNeighbours(), 0.3);
    }

    private double getScore(List<Cell> neighbours, double weight) {
        double tempScore = 0;
        for (Cell c : neighbours) {
            if (isAlive(c)) {
                tempScore += weight;
            }
        }
        return tempScore;
    }

    public boolean isAlive(Cell cell) {
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
