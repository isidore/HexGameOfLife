package org.gameoflife.hex;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void testExceptionIsThrownForInvalidCoordinate() {
        try {
            GameOfLife board1 = new GameOfLife();
            Board board = board1.board;
            board1.board.setAlive(1,2);
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