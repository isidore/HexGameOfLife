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
                    GameOfLifeBoard board = new GameOfLifeBoard();
                    board.setAlive(x,y);

                    result.append(String.format("\n X: %s, Y: %s\n", x, y));
                    String hexBoard = HexPrinter.print(board);
                    result.append(hexBoard);
                }
            }
        }

        Approvals.verify(result);
    }

}