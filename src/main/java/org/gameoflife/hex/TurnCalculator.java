package org.gameoflife.hex;

import org.lambda.Extendable;
import org.lambda.query.Queryable;

import java.util.List;

public class TurnCalculator implements Extendable<List<Cell>> {
    Queryable<Cell> caller;

    public TurnCalculator() {
    }

    @Override
    public void setCaller(List<Cell> caller) {
        this.caller = (Queryable<Cell>) caller;
    }

    /*static*/ Queryable<Cell> getNextCells(/*this Queryable<Cell> caller,*/GameOfLifeBoard board) {
        return caller.where(c -> GameOfLifeBoard.survivesToNextTurn(board.getNeighbourScore(c), board.isAlive(c)));
    }
}