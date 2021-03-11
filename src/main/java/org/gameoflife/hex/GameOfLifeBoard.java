package org.gameoflife.hex;

import com.spun.util.FormattedException;

import java.util.*;

public class GameOfLifeBoard {
    public class Board {
        private List<Cell> liveCells;

        public List<Cell> getLiveCells() {
            return liveCells;
        }

        public void setLiveCells(List<Cell> liveCells) {
            this.liveCells = liveCells;
        }

    }

    public List<Cell> getLiveCells() {
        return liveCells;
    }

    public void setLiveCells(List<Cell> liveCells) {
        this.liveCells = liveCells;
        this.board.liveCells = liveCells;
    }

    private List<Cell> liveCells;
    private Board board = new Board();

    public GameOfLifeBoard() {
        setLiveCells(new ArrayList<>());
    }

    public GameOfLifeBoard(List<Cell> liveCells) {
        setLiveCells(liveCells);
    }

    public static boolean isValidCoordinate(int x, int y) {
        boolean xIsEven = x%2 == 0;
        boolean yIsEven = y%2 == 0;

        return yIsEven == xIsEven;
    }

    public boolean isAlive(Cell cell) {
        return getLiveCells().contains(cell);
    }

    public void setAlive(int x, int y) {
        if (!isValidCoordinate(x, y)) {
            throw new FormattedException("Invalid Location for (%s, %s)", x, y);
        }

        getLiveCells().add(new Cell(x, y));
    }

    @Override
    public String toString() {
        return HexPrinter.print(this);
    }

    public GameOfLifeBoard advanceTurn() {
        ArrayList<Cell> nextLivingCells = new ArrayList<>();
        for (Cell cell : getLiveCells()) {
            if (survivesToNextTurn(getNeighbourScore(cell))) {
                nextLivingCells.add(cell);
            }
        }
        return new GameOfLifeBoard(nextLivingCells);
    }

    private static boolean survivesToNextTurn(double sum) {
        return 2 <= sum && sum <= 3.3;
    }

    private double getNeighbourScore(Cell cell) {
        return getScore(cell.getLevelOneNeighbours(), 1) + getScore(cell.getLevelTwoNeighbours(), 0.3);
    }

    private double getScore(List<Cell> neighbours, double weight) {
        double tempScore = 0;
        for (Cell cell : neighbours) {
            if (isAlive(cell)) {
                tempScore += weight;
            }
        }
        return tempScore;
    }


    private Set<Cell> getLiveCellsAndNeighbours() {
        Set<Cell> liveCellsAndNeighbours = new HashSet<>(getLiveCells());

        for (Cell cell : getLiveCells()) {
            liveCellsAndNeighbours.addAll(cell.getAllNeighbours());
        }

        return liveCellsAndNeighbours;
    }

}
