package org.gameoflife.hex;

import org.lambda.query.Queryable;

import java.util.*;

public class GameOfLife {

    final Board board;

    public GameOfLife() {
        this(new ArrayList<>());
    }

    public GameOfLife(List<Cell> liveCells) {
        this.board = new Board();
        this.board.setLiveCells(liveCells);
    }

    public GameOfLife advanceTurn() {
        Queryable<Cell> nextLivingCells  = getLiveCellsAndNeighbours()
                .where(c -> survivesToNextTurn(getNeighbourScore(board, c), board.isAlive(c)));

        return new GameOfLife(nextLivingCells);
    }

    public static boolean survivesToNextTurn(double sum, boolean alive) {
        boolean survives = 2 <= sum && sum <= 3.3 && alive;
        boolean born = 2.3 <= sum && sum <= 2.9;
        return survives || born;
    }

    public static double getNeighbourScore(Board board, Cell cell) {
        double score = 0.0;
        score += getScore(board, cell.getLevelOneNeighbours(), 1);
        score += getScore(board, cell.getLevelTwoNeighbours(), 0.3);
        return score;
    }

    private static double getScore(Board board, List<Cell> neighbours, double weight) {
        double score = 0;

        for (Cell cell : neighbours) {
            if (board.isAlive(cell)) {
                score += weight;
            }
        }

        return score;
    }


    private Queryable<Cell> getLiveCellsAndNeighbours() {
        Set<Cell> liveCellsAndNeighbours = new HashSet<>(board.getLiveCells());

        for (Cell cell : board.getLiveCells()) {
            liveCellsAndNeighbours.addAll(cell.getAllNeighbours());
        }

        return Queryable.as(new ArrayList<>(liveCellsAndNeighbours));
    }

    @Override
    public String toString() {
        return this.board.toString();
    }
}
