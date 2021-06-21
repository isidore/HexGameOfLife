package org.gameoflife.hex.graphics;

import org.gameoflife.hex.graphics.Hexagon;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HexagonTest {
    @Test
    void testCalculateBoundsWhenGivenRadius() {
        int radius = 10;
        Dimension pixelSize = Hexagon.getDimensionsForRadius(radius);
        assertEquals(new Dimension(16,20), pixelSize);
    }
}