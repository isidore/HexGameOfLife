package org.gameoflife.hex;

import org.approvaltests.Approvals;
import org.approvaltests.reporters.QuietReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@UseReporter(QuietReporter.class)
class BoardTest {

    @Test
    void printWithCellsAtSpecifiedPosition() {
        List<Cell> cells = new ArrayList<>();
        cells.add(new Cell(4, 4));

        GameOfLife game = new GameOfLife(cells);

        Approvals.verify(game);
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