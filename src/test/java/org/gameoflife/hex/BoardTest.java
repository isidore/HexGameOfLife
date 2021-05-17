package org.gameoflife.hex;

import org.approvaltests.Approvals;
import org.gameoflife.hex.game.HexGameOfLife;
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
        assertTrue(GameOfLife.isValidCoordinates(new Coordinates(1, 1)));
        assertTrue(GameOfLife.isValidCoordinates(new Coordinates(2, 2)));
        assertFalse(GameOfLife.isValidCoordinates(new Coordinates(2, 1)));
        assertFalse(GameOfLife.isValidCoordinates(new Coordinates(1, 2)));
    }
}