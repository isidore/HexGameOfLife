package org.gameoflife.hex;

import org.junit.jupiter.api.Test;

class HexagonTest {
    @Test
    void testY() {
        Hexagon h1 = new Hexagon(10, new Coordinates(0, 0));
        Hexagon h2 = new Hexagon(10, new Coordinates(0, 2));
        // Approvals.verifyAll("", Queryable.as(h1,h2), h -> ArrayUtils.toString(h.getPoints(), p -> String.format("(%s,%s)", p.x,p.y)));
    }
}