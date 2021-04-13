package org.gameoflife.hex;

import org.lambda.query.Queryable;

import java.awt.*;

public class Hexagon {
    private final int radius;
    private final Point center;

    public Hexagon(int radius, Coordinates center) {
        this.radius = radius;
        this.center = getCenterPointInPixels(radius, center);
    }

    private static Point getCenterPointInPixels(int radius, Coordinates coordinates) {
        Point topLeftOfBoundingBox = getTopLeftPointOfBoundingBox(radius, coordinates);
        return centerPointInHexagon(radius, topLeftOfBoundingBox);
    }

    private static Point getTopLeftPointOfBoundingBox(int radius, Coordinates coordinates) {
        Point point = new Point();

        point.x = coordinates.getX() * getHalfHexWidth(radius);
        point.y = (int) (coordinates.getY() * getHalfHexHeight(radius) * 1.5);

        return point;
    }

    private static Point centerPointInHexagon(int radius, Point point){
        Point centeredPoint = new Point();

        centeredPoint.x = point.x + getHalfHexWidth(radius);
        centeredPoint.y = point.y + getHalfHexHeight(radius);

        return centeredPoint;
    }

    public Polygon getPolygon() {
        Queryable<Point> points = getPoints();

        int[] pointsX = points.select(p->p.x).stream().mapToInt(Integer::intValue).toArray();
        int[] pointsY = points.select(p->p.y).stream().mapToInt(Integer::intValue).toArray();

        return new Polygon(pointsX, pointsY, 7);
    }

    private static int getHalfHexHeight(int radius) {
        return radius;
    }

    private static int getHalfHexWidth(int radius) {
        return (int) (radius * Math.sin(Math.PI * 2 / 6));
    }

    private Queryable<Point> getPoints() {
        int halfHexWidth = getHalfHexWidth(radius);
        int halfHexHeight = getHalfHexHeight(radius);
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

    public Point getCenter() {
        return this.center;
    }
}
