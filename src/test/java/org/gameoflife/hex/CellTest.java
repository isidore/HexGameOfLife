package org.gameoflife.hex;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CellTest {
    @Test
    void oddsGoTogether() {
        assertEquals(true, GameOfLifeBoard.isValidCoordinate(1,1));
        assertEquals(true, GameOfLifeBoard.isValidCoordinate(2,2));
        assertEquals(false, GameOfLifeBoard.isValidCoordinate(2,1));
        assertEquals(false, GameOfLifeBoard.isValidCoordinate(1,2));
    }

    @Test
    void getLevelOneNeighbours() {
        GameOfLifeBoard gameOfLifeBoard = BoardTest.createBoardWithNeighbours(6, 0);
        Approvals.verify(HexPrinter.print(gameOfLifeBoard));
    }
    @Test
    void getLevelTwoNeighbours() {
        GameOfLifeBoard gameOfLifeBoard = BoardTest.createBoardWithNeighbours(0, 6);
        Approvals.verify(HexPrinter.print(gameOfLifeBoard));
    }

}
