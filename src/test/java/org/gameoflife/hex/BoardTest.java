package org.gameoflife.hex;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    static void printWithCellsAtSpecifiedPosititon(GameOfLifeTest gameOfLifeTest) {
        gameOfLifeTest.extracted();
    }

    @Test
    void testEmptyBoard() {
        final GameOfLife gameOfLife = new GameOfLife();
        Approvals.verify(HexPrinter.print(gameOfLife.board));
    }

    @Test
    void testExceptionIsThrownForInvalidCoordinate() {
        try {
            new Board().setAlive(1,2);
            fail("Coordinates were invalid, expected exception");
        }catch (Exception e) {
            Approvals.verify(e);
        }
    }

    @Test
    void oddsGoTogether() {
        assertTrue(Board.isValidCoordinate(1, 1));
        assertTrue(Board.isValidCoordinate(2, 2));
        assertFalse(Board.isValidCoordinate(2, 1));
        assertFalse(Board.isValidCoordinate(1, 2));
    }
}