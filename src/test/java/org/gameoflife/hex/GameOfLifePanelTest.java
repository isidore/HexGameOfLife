package org.gameoflife.hex;

import com.spun.util.NumberUtils;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifePanelTest {
    @Test
    void testFindHex()
    {
        assertFindHex(15, 15, "(0,0)");
        assertFindHex(159, 104, "(9,3)");
    }

    @Test
    void testResize() {
        {        // resize grid to a single hex
            Hexagon hexagon = new Hexagon(20, new Coordinates(0, 0));
            Point center = hexagon.getCenter();
            // check the grid is 1 x 1
            Dimension leftBottomEdge = new Dimension(center.x * 2, center.y * 2);
            Dimension grid = GameOfLifePanel.getGridWidthAndHeightForPixels(20, leftBottomEdge);
            assertEquals(0 + 1, grid.height);
            assertEquals((0 + 1) * 2, grid.width);
        }

        {
            Hexagon hexagon = new Hexagon(20, new Coordinates(2, 0));
            Point center = hexagon.getCenter();
            // check the grid is 1 x 1
            Dimension leftBottomEdge = new Dimension(center.x * 2, center.y * 2);
            Dimension grid = GameOfLifePanel.getGridWidthAndHeightForPixels(20, leftBottomEdge);
            assertEquals(0 + 1, grid.height);
            assertEquals((0 + 1) * 2, grid.width);
        }
    }

    @Test
    void testHexCanFindItself() {
        for (int i = 0; i < 1000 ; i++) {
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
        Coordinates found = panel.getGridAt(center);
        // Check you are where you started
        assertEquals(original, found);
    }

    private Coordinates hexGenerator() {
        int x = NumberUtils.getRandomInt(0, 10);
        int y = NumberUtils.getRandomInt(0, 9);

        if (!Board.isValidCoordinate(x,y)){
            y++;
        }

        Coordinates original = new Coordinates(x, y);
        return original;
    }

    private void assertFindHex(int x, int y, String expected) {
        GameOfLifePanel gameOfLifePanel = new GameOfLifePanel();
        Coordinates gridAt = gameOfLifePanel.getGridAt(new Point(x, y));
        assertTrue(Board.isValidCoordinate(gridAt),"Invalid grid:" + gridAt);
        assertEquals(expected, gridAt.toString());
    }
}