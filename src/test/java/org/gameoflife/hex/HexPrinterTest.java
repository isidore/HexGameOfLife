package org.gameoflife.hex;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HexPrinterTest {
    @Test
    void testPrintInCorrectLocation() {
        StringBuilder result = new StringBuilder();

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if((x % 2 == 0 && y % 2 == 0) || (x % 2 == 1 && y % 2 == 1)){
                    GameOfLifeBoard board = new GameOfLifeBoard();
                    board.setAlive(x,y);

                    result.append(String.format("\n X: %s, Y: %s\n", x, y));
                    result.append(HexPrinter.print(board));
                }
            }
        }

        Approvals.verify(result);
    }
}