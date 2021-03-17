package org.gameoflife.hex;

import dk.ilios.asciihexgrid.AsciiBoard;
import dk.ilios.asciihexgrid.printers.SmallFlatAsciiHexPrinter;

public class HexPrinter {
    public static String print(Board board) {
        AsciiBoard asciiBoard = new AsciiBoard(0, 100, 0, 100, new SmallFlatAsciiHexPrinter());
        int size = 10;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (Board.isValidCoordinate(x, y)) {
                    if (board.isAlive(new Cell(x, y))) {
                        asciiBoard.printHex("X", "X", 'X', translateX(x), translateY(x, y));
                    } else {
                        asciiBoard.printHex(x + "," + y, "", ' ', translateX(x), translateY(x, y));

                    }
                }
            }
        }
        return asciiBoard.prettPrint(true);
    }

    private static int translateY(int x, int y) {
        int emptyspaces = (y + 1) / 2;
        return 10 + y - (x / 2) - emptyspaces;

    }

    private static int translateX(int x) {
        return x;
    }
}
