package org.gameoflife.hex;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    void oddsGoTogether() {
        assertTrue(GameOfLifeBoard.Board.isValidCoordinate(1, 1));
        assertTrue(GameOfLifeBoard.Board.isValidCoordinate(2, 2));
        assertFalse(GameOfLifeBoard.Board.isValidCoordinate(2, 1));
        assertFalse(GameOfLifeBoard.Board.isValidCoordinate(1, 2));
    }

    @Test
    void testExceptionIsThrownForInvalidCoordinate() {
        try {
            GameOfLifeBoard board = new GameOfLifeBoard();
            board.board.setAlive(1,2);
            fail("Coordinates were invalid, expected exception");
        }catch (Exception e) {
            Approvals.verify(e);
        }
    }

    @Test
    void printEmptyBoardAsHex() {
        final GameOfLifeBoard gameOfLifeBoard = new GameOfLifeBoard();
        Approvals.verify(HexPrinter.print(gameOfLifeBoard, gameOfLifeBoard.board));
    }

    @Test
    void printWithCellsAtSpecifiedPosititon() {
        List<Cell> cells = new ArrayList<>();
        cells.add(new Cell(4, 4));

        GameOfLifeBoard game = new GameOfLifeBoard(cells);

        Approvals.verify(game);
    }

    @Test
    void testZeroDies() {
        String timeline = "";
        GameOfLifeBoard gameOfLife = new GameOfLifeBoard();
        gameOfLife.board.setAlive(4, 4);
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
        gameOfLife.board.setAlive(4, 4);
        gameOfLife.board.setAlive(3, 5);
        gameOfLife.board.setAlive(4, 6);
        gameOfLife.board.setAlive(5, 5);
        timeline += HexPrinter.print(gameOfLife, gameOfLife.board);
        GameOfLifeBoard gameOfLife2 = gameOfLife.advanceTurn();
        timeline += "\n NEXT TURN \n";
        timeline += HexPrinter.print(gameOfLife2, gameOfLife2.board);
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
        GameOfLifeBoard gameOfLifeBoard = createBoardWithNeighbours(numberOfLevelOneNeighbours, numberOfLevelTwoNeighbours, true);
        return printAdvanceBoard(title, gameOfLifeBoard);
    }

    private String printAdvanceBoard(String title, GameOfLifeBoard gameOfLifeBoard) {
        String timeline = "";
        timeline += title;


        timeline += HexPrinter.print(gameOfLifeBoard, gameOfLifeBoard.board);
        timeline += "\n NEXT TURN \n";
        GameOfLifeBoard postTurnBoard = gameOfLifeBoard.advanceTurn();
        timeline += HexPrinter.print(postTurnBoard, postTurnBoard.board);
        return timeline;
    }

    @Test
    void testCentreComesAlive() {
        GameOfLifeBoard boardWithNeighbours = createBoardWithNeighbours(2, 3, false);
        Approvals.verify(printAdvanceBoard("Centre comes alive\n", boardWithNeighbours));
    }

    public static GameOfLifeBoard createBoardWithNeighbours(int numberOfLevelOneNeighbours, int numberOfLevelTwoNeighbours, boolean includeCentre) {
        Cell centre = new Cell(4, 4);
        ArrayList<Cell> cells = new ArrayList<>();

        if (includeCentre) {
            cells.add(centre);
        }

        List<Cell> level1 = centre.getLevelOneNeighbours().subList(0, numberOfLevelOneNeighbours);
        cells.addAll(level1);

        List<Cell> level2 = centre.getLevelTwoNeighbours().subList(0, numberOfLevelTwoNeighbours);
        cells.addAll(level2);

        return new GameOfLifeBoard(cells);
    }
}
