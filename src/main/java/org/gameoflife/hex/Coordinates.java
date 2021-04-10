package org.gameoflife.hex;

public class Coordinates {

    private int y;
    private int x;

    public Coordinates(int x, int y) {
        this.y = y;
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
