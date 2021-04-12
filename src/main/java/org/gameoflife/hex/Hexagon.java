package org.gameoflife.hex;

import org.lambda.query.Queryable;

import java.awt.*;

public class Hexagon {
    private final int radius;
    private final Point center;

    public Hexagon(int radius, Coordinates center) {
        this.radius = radius;

        this.center = getCenterPointInPixels(center);
    }

    public Point getCenterPointInPixels(Coordinates coordinates) {
        return centerPointInHexagon(translateCoordinatesToTopRightPointOfBoundingBox(coordinates));
    }

    private Point translateCoordinatesToTopRightPointOfBoundingBox(Coordinates coordinates) {
        Point point = new Point();

        point.x = coordinates.getX() * getHalfHexWidth();
        point.y = (int) (coordinates.getY() * getHalfHexHeight() * 1.5);

        return point;
    }

    private Point centerPointInHexagon(Point point){
        Point centeredPoint = new Point();

        centeredPoint.x = point.x + getHalfHexWidth();
        centeredPoint.y = point.y + getHalfHexHeight();

        return centeredPoint;
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

    private int getHalfHexWidth() {
        return (int) (radius * Math.sin(Math.PI * 2 / 6));
    }

    public Queryable<Point> getPoints() {
        int halfHexWidth = getHalfHexWidth();
        int halfHexHeight = getHalfHexHeight();
        int halfSideLength = radius / 2;

        return Queryable.as(new Point(center.x - halfHexWidth, center.y - halfSideLength),
                new Point(center.x, center.y - halfHexHeight),
                new Point(center.x + halfHexWidth, center.y - halfSideLength),
                new Point(center.x + halfHexWidth, center.y + halfSideLength),
                new Point(center.x, center.y + halfHexHeight),
                new Point(center.x - halfHexWidth, center.y + halfSideLength),
                new Point(center.x - halfHexWidth, center.y - halfSideLength)
        );
    }
}
