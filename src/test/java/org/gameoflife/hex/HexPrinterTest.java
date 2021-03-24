package org.gameoflife.hex;

import org.approvaltests.Approvals;
import org.approvaltests.awt.AwtApprovals;
import org.approvaltests.reporters.ClipboardReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

class HexPrinterTest {
    @Test
    @UseReporter(ClipboardReporter.class)
    void testGui() {
        GameOfLife game = GameOfLifeTest.createGameWithNeighbours(3, 3, true);
        AwtApprovals.verify(new GameOfLifePanel(game));
    }

    @Test
    void testPrintInCorrectLocation() {
        StringBuilder result = new StringBuilder();

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if(Board.isValidCoordinate(x, y)){
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
        GameOfLife board = new GameOfLife();
        board.board.setAlive(x, y);

        return HexPrinter.print(board.board);
    }

}