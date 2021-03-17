package org.gameoflife.hex;

import org.lambda.query.Queryable;

import java.util.List;

public class TurnHelper implements org.lambda.Extendable<java.util.List<Cell>> {
    private Queryable<Cell> caller;

    @Override
    public void setCaller(List<Cell> caller) {
        this.caller = (Queryable<Cell>) caller;
    }

    public/*static*/ Queryable<Cell> getNextCells(/*this Queryable<Cell> caller,*/GameOfLifeBoard board) {
        return caller.where(c -> GameOfLifeBoard.survivesToNextTurn(board.getNeighbourScore(c), board.isAlive(c)));
    }

    public static Queryable<Cell> getNextCells( Queryable<Cell> caller,GameOfLifeBoard board) {
        return caller.where(c -> GameOfLifeBoard.survivesToNextTurn(board.getNeighbourScore(c), board.isAlive(c)));
    }
}
