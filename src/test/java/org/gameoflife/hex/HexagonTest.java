package org.gameoflife.hex;

import org.gameoflife.hex.game.Hexagon;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;

class HexagonTest {
    @Test
    void testCalculateBoundsWhenGivenRadius() {
        int radius = 10;
        Dimension pixelSize = Hexagon.getDimensionsForRadius(radius);
        assertEquals(new Dimension(16,20), pixelSize);
    }
}