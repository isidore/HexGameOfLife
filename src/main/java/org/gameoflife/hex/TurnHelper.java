package org.gameoflife.hex;

import org.lambda.query.Queryable;

import java.util.List;

public class TurnHelper implements org.lambda.Extendable<java.util.List<Cell>> {
    private Queryable<Cell> caller;

    @Override
    public void setCaller(List<Cell> caller) {
        this.caller = (Queryable<Cell>) caller;
    }

    public Queryable<Cell> getNextCells( GameOfLifeBoard board ) {
        return caller.where(c -> GameOfLifeBoard.survivesToNextTurn(GameOfLifeBoard.getNeighbourScore(board, c), board.board.isAlive(c)));
    }

}
