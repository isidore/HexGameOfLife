package org.gameoflife.hex.game;

import org.approvaltests.Approvals;
import org.gameoflife.hex.Coordinates;
import org.gameoflife.hex.HexPrinter;
import org.junit.jupiter.api.Assertions;
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

        HexGameOfLife game = new HexGameOfLife(cells);

        Approvals.verify(game);
    }

    @Test
    void testEmptyBoard() {
        final HexGameOfLife hexGameOfLife = new HexGameOfLife();
        Approvals.verify(HexPrinter.print(hexGameOfLife));
    }

    @Test
    void oddsGoTogether() {
        Assertions.assertTrue(HexGameOfLife.isValidCoordinates(new Coordinates(1, 1)));
        assertTrue(HexGameOfLife.isValidCoordinates(new Coordinates(2, 2)));
        assertFalse(HexGameOfLife.isValidCoordinates(new Coordinates(2, 1)));
        assertFalse(HexGameOfLife.isValidCoordinates(new Coordinates(1, 2)));
    }
}