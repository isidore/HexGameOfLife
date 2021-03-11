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

    private Board board = new Board();

    public GameOfLifeBoard() {
        this.board.setLiveCells(new ArrayList<Cell>());
    }

    public GameOfLifeBoard(List<Cell> liveCells) {
        this.board.setLiveCells(liveCells);
    }

    public static boolean isValidCoordinate(int x, int y) {
        boolean xIsEven = x%2 == 0;
        boolean yIsEven = y%2 == 0;

        return yIsEven == xIsEven;
    }

    public boolean isAlive(Cell cell) {
        return board.getLiveCells().contains(cell);
    }

    public void setAlive(int x, int y) {
        if (!isValidCoordinate(x, y)) {
            throw new FormattedException("Invalid Location for (%s, %s)", x, y);
        }

        board.getLiveCells().add(new Cell(x, y));
    }

    @Override
    public String toString() {
        return HexPrinter.print(this);
    }

    public GameOfLifeBoard advanceTurn() {
        ArrayList<Cell> nextLivingCells = new ArrayList<>();
        for (Cell cell : getLiveCellsAndNeighbours()) {
            if (survivesToNextTurn(getNeighbourScore(cell), isAlive(cell))) {
                nextLivingCells.add(cell);
            }
        }
        return new GameOfLifeBoard(nextLivingCells);
    }

    private static boolean survivesToNextTurn(double sum, boolean alive) {
        boolean survives = 2 <= sum && sum <= 3.3 && alive;
        boolean born = 2.3 <= sum && sum <= 2.9;
        return survives || born;
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
        Set<Cell> liveCellsAndNeighbours = new HashSet<>(board.getLiveCells());

        for (Cell cell : board.getLiveCells()) {
            liveCellsAndNeighbours.addAll(cell.getAllNeighbours());
        }

        return liveCellsAndNeighbours;
    }

}
