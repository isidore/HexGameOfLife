package org.gameoflife.hex;

import com.spun.swing.Paintable;
import com.spun.util.NumberUtils;
import org.approvaltests.Approvals;
import org.approvaltests.awt.AwtApprovals;
import org.approvaltests.core.Options;
import org.gameoflife.hex.game.HexGameOfLife;
import org.gameoflife.hex.game.HexGameOfLifeTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.lambda.functions.Function1;

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

    @Disabled
    @Test
    void testSequence() {
        // create game of life
        GameOfLifePanel panel = new GameOfLifePanel(setupInitialScenario());
        verifyMultipleFrames(4, (frameNumber) -> {
            panel.advanceTurn();
            return panel;
        });
    }

    private void verifyMultipleFrames(int numberOfFrames, Function1<Integer, Paintable> frameGetter) {
        Approvals.verify(new PaintableMultiframeWriter(numberOfFrames, frameGetter), new Options());
    }

    //Only checking maintained functionality... This test is wrong...
    private void testGetPixelsForCoordinates(Coordinates gridCoordinates) {
        Hexagon hexagon = new Hexagon(20, gridCoordinates);
        Point center = hexagon.getCenter();

        Dimension leftBottomEdge = new Dimension(center.x * 2, center.y * 2);
        Dimension grid = GameOfLifePanel.getGridWidthAndHeightForPixels(20, leftBottomEdge);

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