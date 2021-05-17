package org.gameoflife.hex;

import org.approvaltests.Approvals;
import org.gameoflife.hex.game.Cell;
import org.gameoflife.hex.game.HexGameOfLife;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class HexGameOfLifeTest {


    @Test
    static void testExceptionIsThrownForInvalidCoordinate() {
        Approvals.verifyException(() ->
        {
            HexGameOfLife hexGameOfLife = new HexGameOfLife();
            hexGameOfLife.setAlive(1, 2);
        });
    }

    @Test
    void testZeroDies() {
        String timeline = "";
        HexGameOfLife hexGameOfLife = new HexGameOfLife();
        hexGameOfLife.setAlive(4, 4);
        timeline += hexGameOfLife;
        HexGameOfLife hexGameOfLife2 = hexGameOfLife.advanceTurn();
        timeline += "\n NEXT TURN \n";
        timeline += hexGameOfLife2;
        Approvals.verify(timeline);
    }

    @Test
    void testTwoFirstLevelNeighbours() {
        String timeline = "";
        HexGameOfLife hexGameOfLife = new HexGameOfLife();
        hexGameOfLife.setAlive(4, 4);
        hexGameOfLife.setAlive(3, 5);
        hexGameOfLife.setAlive(4, 6);
        hexGameOfLife.setAlive(5, 5);
        timeline += HexPrinter.print(hexGameOfLife);
        HexGameOfLife hexGameOfLife2 = hexGameOfLife.advanceTurn();
        timeline += "\n NEXT TURN \n";
        timeline += HexPrinter.print(hexGameOfLife2);
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
        HexGameOfLife hexGameOfLife = createGameWithNeighbours(numberOfLevelOneNeighbours, numberOfLevelTwoNeighbours, true);
        return printAdvanceBoard(title, hexGameOfLife);
    }

    private String printAdvanceBoard(String title, HexGameOfLife hexGameOfLife) {
        String timeline = "";
        timeline += title;


        timeline += HexPrinter.print(hexGameOfLife);
        timeline += "\n NEXT TURN \n";
        HexGameOfLife postTurnBoard = hexGameOfLife.advanceTurn();
        timeline += HexPrinter.print(postTurnBoard);
        return timeline;
    }

    @Test
    void testCentreComesAlive() {
        HexGameOfLife boardWithNeighbours = createGameWithNeighbours(2, 3, false);
        Approvals.verify(printAdvanceBoard("Centre (4,4) comes alive\n", boardWithNeighbours));
    }

    public static HexGameOfLife createGameWithNeighbours(int numberOfLevelOneNeighbours, int numberOfLevelTwoNeighbours, boolean includeCentre) {
        Cell centre = new Cell(4, 4);
        ArrayList<Cell> cells = new ArrayList<>();

        if (includeCentre) {
            cells.add(centre);
        }

        List<Cell> level1 = centre.getLevelOneNeighbours().subList(0, numberOfLevelOneNeighbours);
        cells.addAll(level1);

        List<Cell> level2 = centre.getLevelTwoNeighbours().subList(0, numberOfLevelTwoNeighbours);
        cells.addAll(level2);

        return new HexGameOfLife(cells);
    }
}
