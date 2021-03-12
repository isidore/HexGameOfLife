package org.gameoflife.hex;

import com.spun.util.FormattedException;
import org.lambda.query.Queryable;

import java.util.*;

public class GameOfLifeBoard implements org.lambda.Extendable<java.util.List<org.gameoflife.hex.Cell>>{
    private Queryable<Cell> caller;

    @Override
    public void setCaller(List<Cell> caller) {
        this.caller = (Queryable<Cell>) caller;
    }

    private final Board board;

    public GameOfLifeBoard() {
        this.board = new Board();
        board.setLiveCells(new ArrayList<>());
    }

    public GameOfLifeBoard(List<Cell> liveCells) {
        this.board = new Board();
        this.board.setLiveCells(liveCells);
    }

    public static boolean isValidCoordinate(int x, int y) {
        boolean xIsEven = x % 2 == 0;
        boolean yIsEven = y % 2 == 0;

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
        Queryable<Cell> nextLivingCells  = getLiveCellsAndNeighbours()
                .use(GameOfLifeBoard.class).getNextCells(this);
        return new GameOfLifeBoard(nextLivingCells);
    }

    private /*static*/ Queryable<Cell> getNextCells(/*this Queryable<Cell> caller,*/GameOfLifeBoard board) {
        return caller.where(c -> survivesToNextTurn(board.getNeighbourScore(c), board.isAlive(c)));
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


    private Queryable<Cell> getLiveCellsAndNeighbours() {
        Set<Cell> liveCellsAndNeighbours = new HashSet<>(board.getLiveCells());

        for (Cell cell : board.getLiveCells()) {
            liveCellsAndNeighbours.addAll(cell.getAllNeighbours());
        }

        return Queryable.as(new ArrayList<>(liveCellsAndNeighbours));
    }

}
