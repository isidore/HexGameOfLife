package org.gameoflife.hex;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

class HexPrinterTest {
    @Test
    void testPrintInCorrectLocation() {
        StringBuilder result = new StringBuilder();

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if(isValidCoordinates(x, y)){
                    GameOfLifeBoard board = new GameOfLifeBoard();
                    board.setAlive(x,y);

                    result.append(String.format("\n X: %s, Y: %s\n", x, y));
                    result.append(HexPrinter.print(board));
                }
            }
        }

        Approvals.verify(result);
    }

    private boolean isValidCoordinates(int x, int y) {
        return x % 2 == y % 2;
    }
}