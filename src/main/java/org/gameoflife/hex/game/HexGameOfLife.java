package org.gameoflife.hex.game;

import com.spun.util.FormattedException;
import org.apache.commons.lang.math.DoubleRange;
import org.gameoflife.hex.Coordinates;
import org.gameoflife.hex.GameOfLife;
import org.gameoflife.hex.HexPrinter;
import org.lambda.query.Queryable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HexGameOfLife implements GameOfLife {

    final List<Cell> board;

    public HexGameOfLife() {
        this(new ArrayList<>());
    }

    public HexGameOfLife(List<Cell> liveCells) {
        this.board = liveCells;
    }

    public static boolean survivesToNextTurn(double sum, boolean alive) {
        DoubleRange survivable = new DoubleRange(2, 3.3);
        DoubleRange growth = new DoubleRange(2.3, 2.9);

        boolean survives = survivable.containsDouble(sum) && alive;
        boolean born = growth.containsDouble(sum);
        return survives || born;
    }

    public HexGameOfLife advanceTurn() {
        Queryable<Cell> nextLivingCells = getLiveCellsAndNeighbours()
                .where(c -> HexGameOfLife.survivesToNextTurn(getNeighbourScore(board, c), board.contains(c)));

        return new HexGameOfLife(nextLivingCells);
    }

    private static double getNeighbourScore(List<Cell> board, Cell cell) {
        double score = 0.0;
        score += getScore(board, cell.getLevelOneNeighbours(), 1);
        score += getScore(board, cell.getLevelTwoNeighbours(), 0.3);
        return score;
    }

    private static double getScore(List<Cell> board, List<Cell> neighbours, double weight) {
        double score = 0;

        for (Cell cell : neighbours) {
            if (board.contains(cell)) {
                score += weight;
            }
        }

        return score;
    }

    public void setAlive(int x, int y) {
        if (!GameOfLife.isValidCoordinates(new Coordinates(x, y))) {
            throw new FormattedException("Invalid Location for (%s, %s)", x, y);
        }

        this.board.add(new Cell(x, y));
    }

    private Queryable<Cell> getLiveCellsAndNeighbours() {
        Set<Cell> liveCellsAndNeighbours = new HashSet<>(board);

        for (Cell cell : board) {
            liveCellsAndNeighbours.addAll(cell.getAllNeighbours());
        }

        return Queryable.as(new ArrayList<>(liveCellsAndNeighbours));
    }

    public String toString() {
        return HexPrinter.print(this);
    }

    public boolean isAlive(Cell cell) {
        return board.contains(cell);
    }

    public List<Cell> getLiveCells() {
        return board;
    }
}
