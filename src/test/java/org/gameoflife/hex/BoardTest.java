package org.gameoflife.hex;

import org.approvaltests.Approvals;
import org.approvaltests.namer.NamedEnvironment;
import org.approvaltests.namer.NamerFactory;
import org.junit.jupiter.api.Test;
import org.lambda.query.Query;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BoardTest {
    @Test
    void printEmptyBoard() {
        Approvals.verify(new GameOfLifeBoard());
        // maybe
        /**
         *          ___
         *      ___/   \___
         *     /   \___/   \
         *     \___/   \___/
         *         \___/
         */
    }

    @Test
    void printEmptyBoardAsHex() {
        Approvals.verify(HexPrinter.print(new GameOfLifeBoard()));
    }

    @Test
    void printWithCellsAtSpecifiedPosititon() {
        GameOfLifeBoard game = new GameOfLifeBoard(new Cell[]{new Cell(4, 4)});

        Approvals.verify(game);
    }

    @Test
    void testZeroDies() {
        String timeline = "";
        GameOfLifeBoard gameOfLife = new GameOfLifeBoard();
        gameOfLife.setAlive(4, 4);
        timeline += gameOfLife;
        GameOfLifeBoard gameOfLife2 = gameOfLife.advanceTurn();
        timeline += "\n NEXT TURN \n";
        timeline += gameOfLife2;
        Approvals.verify(timeline);
    }

    @Test
    void testTwoFirstLevelNeighbours() {
        String timeline = "";
        GameOfLifeBoard gameOfLife = new GameOfLifeBoard();
        gameOfLife.setAlive(4, 4);
        gameOfLife.setAlive(3, 5);
        gameOfLife.setAlive(4, 6);
        gameOfLife.setAlive(5, 5);
        timeline += HexPrinter.print(gameOfLife);
        GameOfLifeBoard gameOfLife2 = gameOfLife.advanceTurn();
        timeline += "\n NEXT TURN \n";
        timeline += HexPrinter.print(gameOfLife2);
        Approvals.verify(timeline);
    }

    @Test
    void testEverything() {
        String fullTimeline = "";

        for (int l1 = 0; l1 <= 6; l1++) {
            for (int l2 = 0; l2 <= 6; l2++) {
                Cell cell = new Cell(4, 4);
                List<Cell> level1 = cell.getLevelOneNeighbours();
                List<Cell> level2 = cell.getLevelTwoNeighbours();
                String timeline = "";
                timeline += String.format("Neighbours(Level 1 , Level 2) = (%s, %s) => \n", l1, l2);
                ArrayList<Cell> cells = new ArrayList<>();
                cells.add(cell);
                cells.addAll(level1.subList(0, l1));
                cells.addAll(level2.subList(0, l2));
                GameOfLifeBoard gameOfLifeBoard = new GameOfLifeBoard(cells);
                timeline += HexPrinter.print(gameOfLifeBoard);
                timeline += "\n NEXT TURN \n";

                timeline += HexPrinter.print(gameOfLifeBoard.advanceTurn());
                fullTimeline += timeline + "\n\n\n\n";
            }
        }
        Approvals.verify(fullTimeline);
    }
}
