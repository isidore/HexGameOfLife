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
        timeline += gameOfLife;
        GameOfLifeBoard gameOfLife2 = gameOfLife.advanceTurn();
        timeline += "\n NEXT TURN \n";
        timeline += gameOfLife2;
        Approvals.verify(timeline);
    }

    @Test
    void testEverything() {
        Cell cell = new Cell(4, 4);
        List<Cell> level1 = cell.getLevelOneNeighbours();
        List<Cell> level2 = cell.getLevelTwoNeighbours();
        String timeline = "";

        for (int l1 = 0; l1 < level1.size(); l1++) {
            for (int l2 = 0; l2 < level2.size(); l2++) {
                timeline += String.format("\n\nNeighbours(Level 1 , Level 2) = (%s, %s) => \n", l1, l2);
                ArrayList<Cell> cells = new ArrayList<>();
                cells.add(cell);
                cells.addAll(level1.subList(0, l1));
                cells.addAll(level2.subList(0, l2));
                GameOfLifeBoard gameOfLifeBoard = new GameOfLifeBoard(cells);
                timeline += gameOfLifeBoard;
                timeline += "\n NEXT TURN \n";

                timeline += gameOfLifeBoard.advanceTurn();
            }
        }
        Approvals.verify(timeline);
    }
}
