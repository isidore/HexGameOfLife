package org.gameoflife.hex;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CellTest {

    @Test
    void getLevelOneNeighbours() {
        GameOfLife gameOfLife = BoardTest.createGameWithNeighbours(6, 0, true);
        Approvals.verify(HexPrinter.print(gameOfLife.board));
    }

    @Test
    void getLevelTwoNeighbours() {
        GameOfLife gameOfLife = BoardTest.createGameWithNeighbours(0, 6, true);
        Approvals.verify(HexPrinter.print(gameOfLife.board));
    }

    @Test
    void testGetAllNeighbours() {
        Cell cell = new Cell(4,4);
        List<Cell> allNeighbours = new ArrayList<>(cell.getAllNeighbours());
        GameOfLife gameOfLife = new GameOfLife(allNeighbours);
        Approvals.verify(HexPrinter.print(gameOfLife.board));
    }
}
