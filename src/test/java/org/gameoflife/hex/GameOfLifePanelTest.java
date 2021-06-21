package org.gameoflife.hex;

import com.spun.swing.Paintable;
import com.spun.util.NumberUtils;
import com.spun.util.logger.SimpleLogger;
import org.approvaltests.Approvals;
import org.approvaltests.awt.AwtApprovals;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.DelayedClipboardReporter;
import org.approvaltests.reporters.ImageWebReporter;
import org.approvaltests.reporters.UseReporter;
import org.gameoflife.hex.game.Cell;
import org.gameoflife.hex.game.HexGameOfLife;
import org.gameoflife.hex.game.HexGameOfLifeTest;
import org.junit.jupiter.api.Test;
import org.lambda.actions.Action1;
import org.lambda.functions.Function1;
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

    @Test
    @UseReporter(ImageWebReporter.class)
    void testSequence() {
        // create game of life
        GameOfLifePanel panel = new GameOfLifePanel(setupInitialScenario());
        verifySequence(panel, 4, f -> panel.advanceTurn());
    }

    @Test
    void testTextSequence() {
        // create game of life
        GameOfLifePanel panel = new GameOfLifePanel(setupInitialScenario());
        verifyTextSequence(panel, 4, f -> panel.advanceTurn());
    }

    private void verifyTextSequence(Object initial, int numberOfFrames, Function1<Integer, Object> getNextInSequence) {
        StringBuffer text = new StringBuffer();
        text.append("Initial:\n" + initial + "\n\n");
        for (int i = 1; i <= numberOfFrames; i++) {
            text.append("frame #" + i + ":\n" + getNextInSequence.call(i) + "\n\n");
        }
        Approvals.verify(text);
    }

    private void verifySequence(Paintable initial, int numberOfFrames, Function1<Integer, Paintable> getNextInSequence) {
        AwtApprovals.verifySequence(numberOfFrames + 1, f -> {
            if (f == 0) {
                return initial;
            } else {
                return getNextInSequence.call(f);
            }
        });
    }

    //@Disabled
    @Test
    void testCompellingSequence() {
        // create game of life
        HexGameOfLife hexGameOfLife = generateRandomGameOfLife(10);
        HexGameOfLife gameOfLife2 = new HexGameOfLife(_(2, 4), _(2, 6), _(1, 9), _(1, 3), _(5, 5), _(5, 1), _(3, 1), _(5, 1), _(0, 8), _(7, 9));

        verifyAnimation(gameOfLife2, 33);
    }

    @Test
    void testMoreInterestingSequence() {
        // create game of life
        int numberOfFrames = 42;
        //HexGameOfLife hexGameOfLife = generateInterestingGameOfLife(numberOfFrames);
        HexGameOfLife gameOfLife = new HexGameOfLife(_(2, 4), _(2, 6), _(1, 9), _(1, 3), _(5, 5), _(5, 1), _(3, 1), _(5, 1), _(0, 8), _(7, 9));

        verifyAnimation(gameOfLife, numberOfFrames);
    }

    private void verifyAnimation(HexGameOfLife hexGameOfLife, int numberOfFrames) {
        verifyAnimation(hexGameOfLife,numberOfFrames, p -> {});
    }

        private void verifyAnimation(HexGameOfLife hexGameOfLife, int numberOfFrames, Action1<GameOfLifePanel> panelSetup) {
        HexGameOfLife game = hexGameOfLife;
        GameOfLifePanel panel = new GameOfLifePanel(game);
        panelSetup.call(panel);
        AwtApprovals.verifySequence(numberOfFrames, (frameNumber) -> {
            if (0 < frameNumber) {
                panel.advanceTurn();
            }
            return panel;
        }, new Options(new ImageWebReporter()));
    }

    @Test
    void testBlinker() {
        HexGameOfLife game = new HexGameOfLife(_(2, 2), _(5, 3)
                , _(2, 4)
                , _(4, 4)
                , _(1, 5)
                , _(4, 6)
        );
        verifyAnimation(game, 3);
    }

    @Test
    void testSymmetrical() {
        int offset = 5;
        HexGameOfLife game = new HexGameOfLife(_(8 + offset, 4 + offset), _(12 + offset, 4 + offset), _(10 + offset, 6 + offset));
        verifyAnimation(game, 100, p -> p.onResize(new Dimension(800, 800)));
    }

    //@Test
    void testGeneratorOfBlinker() {
        HexGameOfLife game = generateBlinker(42);
        SimpleLogger.variable("Blinker", game.getLiveCells());
        verifyAnimation(game, 20);
    }

    private HexGameOfLife generateInterestingGameOfLife(int minimumRounds) {
        generator:
        for (int i = 0; i < 10_000; i++) {
            HexGameOfLife originalGame = generateRandomGameOfLife(10);
            HexGameOfLife game = originalGame;
            for (int r = 0; r < minimumRounds; r++) {
                game = game.advanceTurn();
                if (!isAnyCellsVisible(game, new Dimension(20, 10))) {
                    continue generator;
                }
            }
            return originalGame;
        }
        throw new RuntimeException("Could not find an interesting game for " + minimumRounds + " rounds");
    }

    private HexGameOfLife generateBlinker(int minimumRounds) {
        generator:
        for (int i = 0; i < 100_000; i++) {
            //generate game with 3 live cells in random location
            for (int numberOfCells = 3; numberOfCells < 20; numberOfCells++) {

                HexGameOfLife frame1 = generateRandomGameOfLife(numberOfCells);
                HexGameOfLife lastFrame = frame1;
                String initialState = sortedString(frame1);

                for (int j = 0; j < 20; j++) {
                    lastFrame = lastFrame.advanceTurn();
                    if (initialState.equals(sortedString(lastFrame))) {
                        return frame1;
                    }
                }

            }
        }
        throw new RuntimeException("Could not find an interesting game for " + minimumRounds + " rounds");
    }

    private String sortedString(HexGameOfLife frame1) {
        return Query.orderBy(frame1.getLiveCells(), c -> c.toString()).toString();
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
            if (!GameOfLife.isValidCoordinates(new Coordinates(x, y))) {
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