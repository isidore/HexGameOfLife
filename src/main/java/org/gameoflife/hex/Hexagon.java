package org.gameoflife.hex;

import org.lambda.query.Queryable;

import java.awt.*;

public class Hexagon  {
    private final int radius;
    private final int x;
    private final int y;

    public Hexagon(int radius, int x, int y) {
        this.radius = radius;
        this.x = x;
        this.y = y;
    }

    public Polygon getPolygon() {
        int hexWidth = getHexWidth()/2;
        int x = (this.x+1) * hexWidth;
        int hexHeight = getHexHeight()/2;
        int y = (this.y*2+1) * hexHeight;
        int y2 = radius/2;

        int[] pointsx = {x-hexWidth, x, x+hexWidth, x+hexWidth, x, x-hexWidth, x-hexWidth};
        int[] pointsy = {y-y2, y-hexHeight, y-y2, y+y2, y+hexHeight, y+y2, y-y2};

        Polygon polygon = new Polygon(pointsx, pointsy, 7);
        return  polygon;
    }
    private int getHexHeight() {
        return radius * 2;
    }

    private int getHexWidth() {

        return (int) (2 * radius * Math.sin(Math.PI*2/6));
    }

    public Queryable<Point> getPoints() {
        int hexWidth = getHexWidth()/2;
        int x = (this.x+1) * hexWidth;
        int hexHeight = getHexHeight()/2;
        int y = (this.y*2+1) * hexHeight;
        int y2 = radius/2;

        return Queryable.as(new Point(x-hexWidth,y-y2),
                new Point(x, y-hexHeight),
                new Point(x+hexWidth,y-y2),
                new Point(x+hexWidth, y+y2),
                new Point(x,y+hexHeight),
                new Point(x-hexWidth, y+y2),
                new Point(x-hexWidth,y-y2)
        );
    }
}
