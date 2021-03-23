package org.gameoflife.hex;

import javax.swing.*;
import java.awt.*;

public class GameOfLifePanel extends JPanel {
    private GameOfLife game;
    private int radius = 20;

    public GameOfLifePanel(GameOfLife game) {
        this.game = game;
        this.setPreferredSize(new Dimension(getHexWidth()*20, getHexHeight()*20));
    }

    private int getHexHeight() {
        return radius * 2;
    }

    private int getHexWidth() {
        return (int) (2 * radius * Math.sin(Math.PI*2/6));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                if(Board.isValidCoordinate(x,y)) {
                    paintHex(x, y, g);
                }
            }
        }
    }

    private void paintHex(int x, int y, Graphics g) {
        g.setColor(Color.BLACK);
        int hexWidth = getHexWidth()/2;
        x = (x*2+1) * hexWidth;
        int hexHeight = getHexHeight()/2;
        y = (y*2+1) * hexHeight;
        int y2 = (int)(radius * Math.cos(Math.PI*2/6));

        int[] pointsx = {x-hexWidth, x, x+hexWidth, x+hexWidth, x, x-hexWidth, x-hexWidth};
        int[] pointsy = {y-y2, y-hexHeight, y-y2, y+y2, y+hexHeight, y+y2, y-y2};

        g.drawPolygon(new Polygon(pointsx, pointsy, 6));
    }
}
