package org.gameoflife.hex.game;

import org.approvaltests.Approvals;
import org.gameoflife.hex.HexPrinter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CellTest {

    @Test
    void getLevelOneNeighbours() {
        HexGameOfLife hexGameOfLife = HexGameOfLifeTest.createGameWithNeighbours(6, 0, true);
        Approvals.verify(HexPrinter.print(hexGameOfLife));
    }

    @Test
    void getLevelTwoNeighbours() {
        HexGameOfLife hexGameOfLife = HexGameOfLifeTest.createGameWithNeighbours(0, 6, true);
        Approvals.verify(HexPrinter.print(hexGameOfLife));
    }

    @Test
    void testGetAllNeighbours() {
        Cell cell = new Cell(4, 4);
        List<Cell> allNeighbours = new ArrayList<>(cell.getAllNeighbours());
        HexGameOfLife hexGameOfLife = new HexGameOfLife(allNeighbours);
        Approvals.verify(HexPrinter.print(hexGameOfLife));
    }
}
