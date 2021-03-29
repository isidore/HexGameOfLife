package org.gameoflife.hex;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameOfLifeTest {


    @Test
    void testZeroDies() {
        String timeline = "";
        GameOfLife gameOfLife = new GameOfLife();
        gameOfLife.board.setAlive(4, 4);
        timeline += gameOfLife;
        GameOfLife gameOfLife2 = gameOfLife.advanceTurn();
        timeline += "\n NEXT TURN \n";
        timeline += gameOfLife2;
        Approvals.verify(timeline);
    }

    @Test
    void testTwoFirstLevelNeighbours() {
        String timeline = "";
        GameOfLife gameOfLife = new GameOfLife();
        gameOfLife.board.setAlive(4, 4);
        gameOfLife.board.setAlive(3, 5);
        gameOfLife.board.setAlive(4, 6);
        gameOfLife.board.setAlive(5, 5);
        timeline += HexPrinter.print(gameOfLife.board);
        GameOfLife gameOfLife2 = gameOfLife.advanceTurn();
        timeline += "\n NEXT TURN \n";
        timeline += HexPrinter.print(gameOfLife2.board);
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
        GameOfLife gameOfLife = createGameWithNeighbours(numberOfLevelOneNeighbours, numberOfLevelTwoNeighbours, true);
        return printAdvanceBoard(title, gameOfLife);
    }

    private String printAdvanceBoard(String title, GameOfLife gameOfLife) {
        String timeline = "";
        timeline += title;


        timeline += HexPrinter.print(gameOfLife.board);
        timeline += "\n NEXT TURN \n";
        GameOfLife postTurnBoard = gameOfLife.advanceTurn();
        timeline += HexPrinter.print(postTurnBoard.board);
        return timeline;
    }

    @Test
    void testCentreComesAlive() {
        GameOfLife boardWithNeighbours = createGameWithNeighbours(2, 3, false);
        Approvals.verify(printAdvanceBoard("Centre (4,4) comes alive\n", boardWithNeighbours));
    }

    public static GameOfLife createGameWithNeighbours(int numberOfLevelOneNeighbours, int numberOfLevelTwoNeighbours, boolean includeCentre) {
        Cell centre = new Cell(4, 4);
        ArrayList<Cell> cells = new ArrayList<>();

        if (includeCentre) {
            cells.add(centre);
        }

        List<Cell> level1 = centre.getLevelOneNeighbours().subList(0, numberOfLevelOneNeighbours);
        cells.addAll(level1);

        List<Cell> level2 = centre.getLevelTwoNeighbours().subList(0, numberOfLevelTwoNeighbours);
        cells.addAll(level2);

        return new GameOfLife(cells);
    }
}
