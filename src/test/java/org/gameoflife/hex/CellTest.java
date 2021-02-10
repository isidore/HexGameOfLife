package org.gameoflife.hex;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CellTest {
    @Test
    void oddsGoTogether() {
        assertEquals(true, Cell.isValid(1,1));
        assertEquals(true, Cell.isValid(2,2));
        assertEquals(false, Cell.isValid(2,1));
        assertEquals(false, Cell.isValid(1,2));
    }

    @Test
    void getLevelOneNeighbours() {
        GameOfLifeBoard gameOfLifeBoard = new GameOfLifeBoard(Cell.getLevelOneNeighbours(2, 4));
        Approvals.verify(gameOfLifeBoard);
    }
    @Test
    void getLevelTwoNeighbours() {
        GameOfLifeBoard gameOfLifeBoard = new GameOfLifeBoard(Cell.getLevelTwoNeighbours(6,4));
        gameOfLifeBoard.setAlive(6,4);
        Approvals.verify(gameOfLifeBoard);
    }

}
