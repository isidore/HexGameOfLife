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
        int y = translateYGridToPixels(hexHeight);
        // 0 => 10
        // 1 => 25
        // 2 => 30
        // 3 => 45
        int y2 = radius/2;

        int[] pointsx = {x-hexWidth, x, x+hexWidth, x+hexWidth, x, x-hexWidth, x-hexWidth};
        int[] pointsy = {y-y2, y-hexHeight, y-y2, y+y2, y+hexHeight, y+y2, y-y2};

        Polygon polygon = new Polygon(pointsx, pointsy, 7);
        return  polygon;
    }

    private int translateYGridToPixels(int hexHeight) {
        switch (y) {
            case 0:
                return hexHeight * 1;
            case 1:
                return (int) (hexHeight * 2.5);
            case 2:
                return hexHeight * 4;
            case 3:
                return (int) (hexHeight * 5.5);
        }
        return (int)( hexHeight * (1+(1.5 * y)));//(int) (this.y / 2.0 + 1) * hexHeight;
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
        int y = translateYGridToPixels(hexHeight);
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
