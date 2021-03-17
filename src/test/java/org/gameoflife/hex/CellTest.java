package org.gameoflife.hex;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CellTest {

    @Test
    void getLevelOneNeighbours() {
        GameOfLifeBoard gameOfLifeBoard = BoardTest.createBoardWithNeighbours(6, 0, true);
        Approvals.verify(HexPrinter.print(gameOfLifeBoard, gameOfLifeBoard.board));
    }

    @Test
    void getLevelTwoNeighbours() {
        GameOfLifeBoard gameOfLifeBoard = BoardTest.createBoardWithNeighbours(0, 6, true);
        Approvals.verify(HexPrinter.print(gameOfLifeBoard, gameOfLifeBoard.board));
    }

    @Test
    void testGetAllNeighbours() {
        Cell cell = new Cell(4,4);
        List<Cell> allNeighbours = new ArrayList<>(cell.getAllNeighbours());
        GameOfLifeBoard gameOfLifeBoard = new GameOfLifeBoard(allNeighbours);
        Approvals.verify(HexPrinter.print(gameOfLifeBoard, gameOfLifeBoard.board));
    }
}
