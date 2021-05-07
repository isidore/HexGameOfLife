package org.gameoflife.hex;

import org.apache.commons.lang.math.DoubleRange;
import org.lambda.query.Queryable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameOfLife {

    final Board board;

    public GameOfLife() {
        this(new ArrayList<>());
    }

    public GameOfLife(List<Cell> liveCells) {
        this.board = new Board();
        this.board.setLiveCells(liveCells);
    }

    static boolean isAlive2(List<Cell> liveCells, Cell cell) {
        return liveCells.contains(cell);
    }

    public GameOfLife advanceTurn() {
        Queryable<Cell> nextLivingCells = getLiveCellsAndNeighbours()
                .where(c -> survivesToNextTurn(getNeighbourScore(board, c), board.isAlive(c)));

        return new GameOfLife(nextLivingCells);
    }

    public static boolean survivesToNextTurn(double sum, boolean alive) {
        DoubleRange survivable = new DoubleRange(2, 3.3);
        DoubleRange growth = new DoubleRange(2.3, 2.9);

        boolean survives = survivable.containsDouble(sum) && alive;
        boolean born = growth.containsDouble(sum);
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

    public void setAlive(int x, int y) {
        this.board.setAlive(x, y);
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

    public boolean isAlive(Cell cell) {
        return board.isAlive(cell);
    }

    public static boolean isValidCoordinates(Coordinates coordinates){
        return Board.isValidCoordinate(coordinates);
    }
}
