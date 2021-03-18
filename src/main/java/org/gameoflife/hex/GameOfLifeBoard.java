package org.gameoflife.hex;

import org.lambda.query.Queryable;

import java.util.*;

public class GameOfLifeBoard {

    final Board board;

    public GameOfLifeBoard() {
        this(new ArrayList<>());
    }

    public GameOfLifeBoard(List<Cell> liveCells) {
        this.board = new Board();
        this.board.setLiveCells(liveCells);
    }

    @Override
    public String toString() {
        return this.board.toString();
    }

    public GameOfLifeBoard advanceTurn() {
        Queryable<Cell> nextLivingCells  = getLiveCellsAndNeighbours()
                .use(TurnHelper.class).getNextCells(this);

        return new GameOfLifeBoard(nextLivingCells);
    }

    public static boolean survivesToNextTurn(double sum, boolean alive) {
        boolean survives = 2 <= sum && sum <= 3.3 && alive;
        boolean born = 2.3 <= sum && sum <= 2.9;
        return survives || born;
    }

    public static double getNeighbourScore(GameOfLifeBoard gameOfLifeBoard, Cell cell) {
        return gameOfLifeBoard.getScore(cell.getLevelOneNeighbours(), 1) + gameOfLifeBoard.getScore(cell.getLevelTwoNeighbours(), 0.3);
    }

    private double getScore(List<Cell> neighbours, double weight) {
        double tempScore = 0;
        for (Cell cell : neighbours) {
            if (board.isAlive(cell)) {
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
