package org.gameoflife.hex;

import com.spun.util.NumberUtils;
import com.spun.util.logger.SimpleLogger;
import org.apache.commons.lang.math.IntRange;
import org.approvaltests.awt.AwtApprovals;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.ImageWebReporter;
import org.approvaltests.reporters.UseReporter;
import org.gameoflife.hex.game.Cell;
import org.gameoflife.hex.game.HexGameOfLife;
import org.gameoflife.hex.game.HexGameOfLifeTest;
import org.junit.jupiter.api.Test;
import org.lambda.query.Query;

import java.awt.*;
import java.util.Optional;

import static org.gameoflife.hex.GameOfLifeRunner.setupInitialScenario;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameOfLifePanelTest {
    @Test
    void testAdvanceTurn() {
        HexGameOfLife game = HexGameOfLifeTest.createGameWithNeighbours(6, 4, false);
        GameOfLifePanel gameOfLifePanel = new GameOfLifePanel(game);

        gameOfLifePanel.advanceTurn();

        AwtApprovals.verify(gameOfLifePanel);
    }

    @Test
    void testFindHex() {
        assertFindHex(15, 15, "(0,0)");
        assertFindHex(159, 104, "(9,3)");
    }

    @Test
    void testResize() {
        testGetPixelsForCoordinates(new Coordinates(0, 0));
        testGetPixelsForCoordinates(new Coordinates(2, 0));
    }

    //@Disabled
    @Test
    @UseReporter(ImageWebReporter.class)
    void testSequence() {
        // create game of life
        GameOfLifePanel panel = new GameOfLifePanel(setupInitialScenario());
        AwtApprovals.verifySequence(4, (frameNumber) -> {
            panel.advanceTurn();
            return panel;
        });
    }

    //@Disabled
    @Test
    @UseReporter(ImageWebReporter.class)
    void testCompellingSequence() {
        // create game of life
        HexGameOfLife hexGameOfLife = generateRandomGameOfLife(10);
        HexGameOfLife gameOfLife2 = new HexGameOfLife(_(2,4), _(2,6), _(1,9), _(1,3), _(5,5), _(5,1), _(3,1), _(5,1), _(0,8), _(7,9));

        verifyAnimation(gameOfLife2, 33);
    }

    @Test
    @UseReporter(ImageWebReporter.class)
    void testMoreInterestingSequence() {
        // create game of life
        int numberOfFrames = 42;
        HexGameOfLife hexGameOfLife = generateInterestingGameOfLife(numberOfFrames);
        HexGameOfLife gameOfLife2 = new HexGameOfLife(_(2,4), _(2,6), _(1,9), _(1,3), _(5,5), _(5,1), _(3,1), _(5,1), _(0,8), _(7,9));

        verifyAnimation(hexGameOfLife, numberOfFrames);
    }

    private void verifyAnimation(HexGameOfLife hexGameOfLife, int numberOfFrames) {
        HexGameOfLife game = hexGameOfLife;
        GameOfLifePanel panel = new GameOfLifePanel(game);
        AwtApprovals.verifySequence(numberOfFrames, (frameNumber) -> {
            if(0 < frameNumber) {panel.advanceTurn();}
            return panel;
        }, new Options(ImageWebReporter.INSTANCE));
    }

    @Test
    void testBlinker() {
        //HexGameOfLife game = new HexGameOfLife(_(8,4), _(12,4), _(10,6));
        HexGameOfLife game = generateBlinker(42);
        verifyAnimation(game,4);
    }

    private HexGameOfLife generateInterestingGameOfLife(int minimumRounds) {
    generator:
        for (int i = 0; i < 10000; i++) {
            HexGameOfLife originalGame = generateRandomGameOfLife(10);
            HexGameOfLife game = originalGame;
            for (int r = 0; r < minimumRounds; r++) {
                game = game.advanceTurn();
                if(!isAnyCellsVisible(game, new Dimension(20, 10))){
                    continue generator;
                }
            }
            return originalGame;
        }
        throw new RuntimeException("Could not find an interesting game for " + minimumRounds + " rounds");
    }

    private HexGameOfLife generateBlinker(int minimumRounds) {
    generator:
        for (int i = 0; i < 1_000_000; i++) {
            //generate game with 3 live cells in random location
            HexGameOfLife frame1 = generateRandomGameOfLife(3);
            HexGameOfLife frame2 = frame1.advanceTurn();
            HexGameOfLife frame3 = frame2.advanceTurn();

            if (frame1.getLiveCells().toString().equals(frame3.getLiveCells().toString())){
                return frame1;
            }
        }
        throw new RuntimeException("Could not find an interesting game for " + minimumRounds + " rounds");
    }

    private boolean isAnyCellsVisible(HexGameOfLife game, Dimension dimension) {
        //IntRange panelHeight = new IntRange(0, dimension.height);
        return Query.any(game.getLiveCells(),
                c -> 0 <= c.getX() && c.getX() <= dimension.width
                        && 0 <= c.getY() && c.getY() <= dimension.height);
    }

    private HexGameOfLife generateRandomGameOfLife(int numCells) {
        HexGameOfLife gameOfLife = new HexGameOfLife();
        for (int i = 0; i < numCells; i++) {
            int x = NumberUtils.getRandomInt(0, 10);
            int y = NumberUtils.getRandomInt(0, 10);
            if(!GameOfLife.isValidCoordinates(new Coordinates(x,y))){
                y++;
            }
            gameOfLife.setAlive(x, y);
        }

        //SimpleLogger.variable("",gameOfLife.getLiveCells());
        return gameOfLife;
    }

    private Cell _(int x, int y) {
        return new Cell(x, y);
    }

    //Only checking maintained functionality... This test is wrong...
    private void testGetPixelsForCoordinates(Coordinates gridCoordinates) {
        Hexagon hexagon = new Hexagon(20, gridCoordinates);
        Point center = hexagon.getCenter();

        Dimension leftBottomEdge = new Dimension(center.x * 2, center.y * 2);
        Dimension grid = Hexagon.getGridWidthAndHeightForPixels(20, leftBottomEdge);

        assertEquals(gridCoordinates.getY() + 1, grid.height);
        assertEquals((gridCoordinates.getX() + 1) * 2, grid.width);
    }

    @Test
    void testHexCanFindItself() {
        for (int i = 0; i < 1000; i++) {
            Coordinates original = hexGenerator();
            assertInverseProperty(original);
        }
    }

    private void assertInverseProperty(Coordinates original) {
        // do
        Hexagon hexagon = new Hexagon(20, original);
        GameOfLifePanel panel = new GameOfLifePanel();
        Point center = hexagon.getCenter();
        // undo
        Optional<Coordinates> found = panel.getGridCoordinatesAt(center);
        // Check you are where you started
        assertEquals(original, found.get());
    }

    private Coordinates hexGenerator() {
        int x = NumberUtils.getRandomInt(0, 10);
        int y = NumberUtils.getRandomInt(0, 9);

        if (!GameOfLife.isValidCoordinates(new Coordinates(x, y))) {
            y++;
        }

        Coordinates original = new Coordinates(x, y);
        return original;
    }

    private void assertFindHex(int x, int y, String expected) {
        GameOfLifePanel gameOfLifePanel = new GameOfLifePanel();
        Coordinates gridAt = gameOfLifePanel.getGridCoordinatesAt(new Point(x, y)).get();
        assertTrue(GameOfLife.isValidCoordinates(gridAt), "Invalid grid:" + gridAt);
        assertEquals(expected, gridAt.toString());
    }
}