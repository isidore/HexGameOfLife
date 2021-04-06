package org.gameoflife.hex;

import org.lambda.query.Queryable;

import java.awt.*;

public class Hexagon {
    private final int radius;
    private final int x;
    private final int y;

    public Hexagon(int radius, int x, int y) {
        Point center = getCenterPointForGrid(x, y,radius);

        this.radius = radius;
        this.x = center.x;
        this.y = center.y;
    }

    public static Point getCenterPointForGrid(int x, int y, int radius) {
        int halfHexWidth = getHexWidth(radius) / 2;
        int halfHexHeight = getHexHeight(radius);

        int xAsPixels = (x + 1) * halfHexWidth;
        int yAsPixels = (int) (halfHexHeight * (1 + (1.5 * y)));

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
        return radius;
    }

    private static int getHexWidth(int radius) {
        return (int) (2 * radius * Math.sin(Math.PI * 2 / 6));
    }

    public Queryable<Point> getPoints() {
        int halfHexWidth = getHexWidth() / 2;
        int halfHexHeight = getHexHeight();
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
