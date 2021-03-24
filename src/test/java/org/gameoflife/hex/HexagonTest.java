package org.gameoflife.hex;

import com.spun.util.ArrayUtils;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;
import org.lambda.query.Queryable;

import static org.junit.jupiter.api.Assertions.*;

class HexagonTest {
    @Test
    void testY() {
        Hexagon h1 = new Hexagon(10, 0, 0);
        Hexagon h2 = new Hexagon(10, 0, 2);
       // Approvals.verifyAll("", Queryable.as(h1,h2), h -> ArrayUtils.toString(h.getPoints(), p -> String.format("(%s,%s)", p.x,p.y)));
    }
}