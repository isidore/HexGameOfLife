package org.gameoflife.hex;

import com.spun.util.Colors;

import javax.swing.*;
import java.awt.*;

public class GameOfLifePanel extends JPanel {
    public static final int BOARD_WIDTH = 20;
    public static final int BOARD_HEIGHT = 10;
    private GameOfLife game;
    private int radius = 20;

    public GameOfLifePanel(GameOfLife game) {
        this.game = game;
        Hexagon hexagon = new Hexagon(radius, BOARD_WIDTH-1, BOARD_HEIGHT-1);
        int x = hexagon.getPoints().max(p -> p.x).x + 1;
        int y = hexagon.getPoints().max(p -> p.y).y + 1;
        this.setPreferredSize(new Dimension(x, y));
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
        g.setColor(Colors.Blues.AliceBlue);
        g.fillRect(0,0,this.getWidth(),this.getHeight());
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                if(Board.isValidCoordinate(x,y)) {
                    paintHex(x, y, g);
                }
            }
        }
    }

    private void paintHex(int x, int y, Graphics g) {

        g.setColor(Color.BLACK);

        Hexagon hexagon = new Hexagon(radius, x,y);
        g.drawPolygon(hexagon.getPolygon());
    }
}
