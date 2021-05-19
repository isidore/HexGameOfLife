package org.gameoflife.hex;

import com.spun.util.NumberUtils;
import com.spun.util.logger.SimpleLogger;
import org.approvaltests.awt.AwtApprovals;
import org.approvaltests.reporters.ImageWebReporter;
import org.approvaltests.reporters.UseReporter;
import org.gameoflife.hex.game.HexGameOfLife;
import org.gameoflife.hex.game.HexGameOfLifeTest;
import org.junit.jupiter.api.Test;

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
        HexGameOfLife gameOfLife = new HexGameOfLife();
        for (int i = 0; i < 10; i++) {
            int x = NumberUtils.getRandomInt(0, 10);
            int y = NumberUtils.getRandomInt(0, 10);
            if(!GameOfLife.isValidCoordinates(new Coordinates(x,y))){
                y++;
            }
            //gameOfLife.setAlive(x, y);
        }

        SimpleLogger.variable("",gameOfLife.getBoard().getLiveCells());
        Point[] points = {_(2,4), _(2,6), _(1,9), _(1,3), _(5,5), _(5,1), _(3,1), _(5,1), _(0,8), _(7,9)};
        for (Point point : points) {
            gameOfLife.setAlive(point.x, point.y);
        }
        HexGameOfLife game = gameOfLife;
        GameOfLifePanel panel = new GameOfLifePanel(game);
        AwtApprovals.verifySequence(33, (frameNumber) -> {
            panel.advanceTurn();
            return panel;
        });
    }

    private Point _(int x, int y) {
        return new Point(x, y);
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