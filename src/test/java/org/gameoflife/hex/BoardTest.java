package org.gameoflife.hex;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
                String timeline = advanceBoardWithNeighbours(l1, l2);
                fullTimeline += timeline + "\n\n\n\n";
            }
        }
        Approvals.verify(fullTimeline);
    }

    private String advanceBoardWithNeighbours(int numberOfLevelOneNeighbours, int numberOfLevelTwoNeighbours) {
        String title = String.format("Neighbours(Level 1 , Level 2) = (%s, %s) => \n", numberOfLevelOneNeighbours, numberOfLevelTwoNeighbours);
        GameOfLifeBoard gameOfLifeBoard = createBoardWithNeighbours(numberOfLevelOneNeighbours, numberOfLevelTwoNeighbours);
        return printAdvanceBoard(title, gameOfLifeBoard);
    }

    private String printAdvanceBoard(String title, GameOfLifeBoard gameOfLifeBoard) {
        String timeline = "";
        timeline += title;


        timeline += HexPrinter.print(gameOfLifeBoard);
        timeline += "\n NEXT TURN \n";
        timeline += HexPrinter.print(gameOfLifeBoard.advanceTurn());
        return timeline;
    }

    public static GameOfLifeBoard createBoardWithNeighbours(int numberOfLevelOneNeighbours, int numberOfLevelTwoNeighbours) {
        Cell centre = new Cell(4, 4);
        ArrayList<Cell> cells = new ArrayList<>();
        cells.add(centre);

        List<Cell> level1 = centre.getLevelOneNeighbours().subList(0, numberOfLevelOneNeighbours);
        cells.addAll(level1);

        List<Cell> level2 = centre.getLevelTwoNeighbours().subList(0, numberOfLevelTwoNeighbours);
        cells.addAll(level2);

        return new GameOfLifeBoard(cells);
    }
}
