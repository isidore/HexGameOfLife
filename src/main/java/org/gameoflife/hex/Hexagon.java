package org.gameoflife.hex;

import org.lambda.query.Queryable;

import java.awt.*;

public class Hexagon {
    private final int radius;
    private final Point center;

    public Hexagon(int radius, Coordinates center) {
        this.radius = radius;

        this.center = getCenterPointInPixels(center.getX(), center.getY());
    }

    public Point getCenterPointInPixels(int x, int y) {
        int halfHexWidth = getHexWidth() / 2;
        int halfHexHeight = getHalfHexHeight();

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

    private int getHalfHexHeight() {
        return radius;
    }

    private int getHexWidth() {
        return (int) (2 * radius * Math.sin(Math.PI * 2 / 6));
    }

    public Queryable<Point> getPoints() {
        int halfHexWidth = getHexWidth() / 2;
        int halfHexHeight = getHalfHexHeight();
        int halfSideLength = radius / 2;

        Point p = pointyHexCorner(1);
        return Queryable.as(new Point(center.x - halfHexWidth, center.y - halfSideLength),
                new Point(center.x, center.y - halfHexHeight),
                pointyHexCorner(0),
                pointyHexCorner(1),
                new Point(center.x, center.y + halfHexHeight),
                new Point(center.x - halfHexWidth, center.y + halfSideLength),
                new Point(center.x - halfHexWidth, center.y - halfSideLength)
        );
    }

    private Point pointyHexCorner(int i){
        double angle_deg = 60 * i - 30;
        double angle_rad = Math.PI / 180 * angle_deg;
        return new Point((int)(center.x + radius * Math.cos(angle_rad)), (int)(center.y + radius * Math.sin(angle_rad)));
    }
}
