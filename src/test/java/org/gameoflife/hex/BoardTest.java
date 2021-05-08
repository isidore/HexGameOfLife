package org.gameoflife.hex;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        Approvals.verifyException(() ->
                new Board().setAlive(1, 2));
    }

    @Test
    void oddsGoTogether() {
        assertTrue(GameOfLifeInterface.isValidCoordinates(new Coordinates(1, 1)));
        assertTrue(GameOfLifeInterface.isValidCoordinates(new Coordinates(2, 2)));
        assertFalse(GameOfLifeInterface.isValidCoordinates(new Coordinates(2, 1)));
        assertFalse(GameOfLifeInterface.isValidCoordinates(new Coordinates(1, 2)));
    }
}