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