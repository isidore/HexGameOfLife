package org.gameoflife.hex;

import dk.ilios.asciihexgrid.AsciiBoard;
import dk.ilios.asciihexgrid.printers.SmallPointyAsciiHexPrinter;

public class HexPrinter {
    public static String print(GameOfLife board) {
        AsciiBoard asciiBoard = new AsciiBoard(0, 100, 0, 100, new SmallPointyAsciiHexPrinter());
        int size = 10;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (GameOfLife.isValidCoordinates(new Coordinates(x, y))) {
                    if (board.getLiveCells().contains(new Cell(x, y))) {
                        asciiBoard.printHex("X", "X", 'X', translateX(x, y), translateY(x, y));
                    } else {
                        asciiBoard.printHex(x + "," + y, "", ' ', translateX(x, y), translateY(x, y));

                    }
                }
            }
        }
        return asciiBoard.prettPrint(true);
    }

    private static int translateY(int x, int y) {
        return y;
    }

    private static int translateX(int x, int y) {
        int emptyspaces = (x + 1) / 2;
        //return 10 + x - (y / 2) - emptyspaces;
        return x - emptyspaces;
    }
}
