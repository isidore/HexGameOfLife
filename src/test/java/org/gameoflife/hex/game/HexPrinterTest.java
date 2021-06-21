package org.gameoflife.hex.game;

import org.approvaltests.Approvals;
import org.gameoflife.hex.game.Coordinates;
import org.gameoflife.hex.game.HexGameOfLife;
import org.gameoflife.hex.game.HexPrinter;
import org.junit.jupiter.api.Test;

class HexPrinterTest {

    @Test
    void testPrintInCorrectLocation() {
        StringBuilder result = new StringBuilder();

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (HexGameOfLife.isValidCoordinates(new Coordinates(x, y))) {
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
        HexGameOfLife game = new HexGameOfLife();
        game.setAlive(x, y);

        return HexPrinter.print(game);
    }

}