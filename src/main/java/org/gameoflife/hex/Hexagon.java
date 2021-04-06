package org.gameoflife.hex;

import org.lambda.query.Queryable;

import java.awt.*;

public class Hexagon {
    private final int radius;
    private final int x;
    private final int y;

    public Hexagon(int radius, Point center) {
        this.radius = radius;
        this.x = center.x;
        this.y = center.y;
    }

    public static Point getCenterPointForGrid(int x, int y, int radius) {
        int hexWidth = getHexWidth(radius) / 2;
        int xAsPixels = (x + 1) * hexWidth;
        int hexHeight = getHexHeight(radius) / 2;
        int yAsPixels = (int) (hexHeight * (1 + (1.5 * y)));
        return new Point(xAsPixels,yAsPixels);
    }


    public Polygon getPolygon() {
        Queryable<Point> points = getPoints();

        int[] pointsX = points.select(p->p.x).stream().mapToInt(Integer::intValue).toArray();
        int[] pointsY = points.select(p->p.y).stream().mapToInt(Integer::intValue).toArray();

        return new Polygon(pointsX, pointsY, 7);
    }

    private int getHexHeight() {
        return getHexHeight(radius);
    }

    private int getHexWidth() {
        return getHexWidth(radius);
    }

    private static int getHexHeight(int radius) {
        return radius * 2;
    }

    private static int getHexWidth(int radius) {
        return (int) (2 * radius * Math.sin(Math.PI * 2 / 6));
    }

    public Queryable<Point> getPoints() {
        int halfHexWidth = getHexWidth() / 2;
        int halfHexHeight = getHexHeight() / 2;
        int halfSideLength = radius / 2;

        return Queryable.as(new Point(x - halfHexWidth, y - halfSideLength),
                new Point(x, y - halfHexHeight),
                new Point(x + halfHexWidth, y - halfSideLength),
                new Point(x + halfHexWidth, y + halfSideLength),
                new Point(x, y + halfHexHeight),
                new Point(x - halfHexWidth, y + halfSideLength),
                new Point(x - halfHexWidth, y - halfSideLength)
        );
    }
}
