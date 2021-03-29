package org.gameoflife.hex;

import org.lambda.query.Queryable;

import java.awt.*;

public class Hexagon {
    private final int radius;
    private final int x;
    private final int y;

    public Hexagon(int radius, int x, int y) {
        this.radius = radius;
        this.x = x;
        this.y = y;
    }

    public Polygon getPolygon() {
        Queryable<Point> points = getPoints();

        int[] pointsx = points.select(p->p.x).stream().mapToInt(Integer::intValue).toArray();
        int[] pointsy = points.select(p->p.y).stream().mapToInt(Integer::intValue).toArray();

        return new Polygon(pointsx, pointsy, 7);
    }

    private int translateYGridToPixels(int hexHeight) {
        return (int) (hexHeight * (1 + (1.5 * y)));
    }

    private int getHexHeight() {
        return radius * 2;
    }

    private int getHexWidth() {

        return (int) (2 * radius * Math.sin(Math.PI * 2 / 6));
    }

    public Queryable<Point> getPoints() {
        int hexWidth = getHexWidth() / 2;
        int x = (this.x + 1) * hexWidth;
        int hexHeight = getHexHeight() / 2;
        int y = translateYGridToPixels(hexHeight);
        int y2 = radius / 2;

        return Queryable.as(new Point(x - hexWidth, y - y2),
                new Point(x, y - hexHeight),
                new Point(x + hexWidth, y - y2),
                new Point(x + hexWidth, y + y2),
                new Point(x, y + hexHeight),
                new Point(x - hexWidth, y + y2),
                new Point(x - hexWidth, y - y2)
        );
    }
}
