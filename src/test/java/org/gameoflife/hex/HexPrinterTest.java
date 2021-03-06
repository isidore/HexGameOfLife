package org.gameoflife.hex;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

class HexPrinterTest {
    @Test
    void testPrintInCorrectLocation() {
        StringBuilder result = new StringBuilder();

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if(Cell.isValid(x, y)){
                    result.append(createTestOutputForBoardWithCellAtCoords(x, y));
                }
            }
        }

        Approvals.verify(result);
    }

    private StringBuilder createTestOutputForBoardWithCellAtCoords(int x, int y) {
        String hexBoard = setUpBoardWithLiveCellAtCoordinates(x, y);

        StringBuilder testOutput = new StringBuilder();
        testOutput.append(String.format("\n X: %s, Y: %s\n", x, y));
        testOutput.append(hexBoard);
        return testOutput;
    }

    private String setUpBoardWithLiveCellAtCoordinates(int x, int y) {
        GameOfLifeBoard board = new GameOfLifeBoard();
        board.setAlive(x, y);
        return HexPrinter.print(board);
    }

}