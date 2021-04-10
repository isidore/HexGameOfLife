package org.gameoflife.hex;

import com.spun.util.Colors;
import com.spun.util.ThreadUtils;
import com.spun.util.WindowUtils;
import org.lambda.query.Queryable;

import javax.swing.*;
import java.awt.*;

public class GameOfLifePanel extends JPanel {
    public static final int BOARD_WIDTH = 20;
    public static final int BOARD_HEIGHT = 10;
    private final int radius = 20;
    private GameOfLife game;

    public GameOfLifePanel(GameOfLife game) {
        this.game = game;
        Hexagon hexagon = new Hexagon(radius, new Coordinates(BOARD_WIDTH - 1, BOARD_HEIGHT - 1));
        Queryable<Point> points = hexagon.getPoints();
        int x = points.max(p -> p.x).x + 1;
        int y = points.max(p -> p.y).y + 1;
        this.setPreferredSize(new Dimension(x, y));
    }

    public static void main(String[] args) {
        GameOfLife gameOfLife = new GameOfLife();
        gameOfLife.board.setAlive(4, 4);
        gameOfLife.board.setAlive(3, 5);
        gameOfLife.board.setAlive(4, 6);
        gameOfLife.board.setAlive(5, 5);

        GameOfLifePanel gameOfLifePanel = new GameOfLifePanel(gameOfLife);
        WindowUtils.testPanel(gameOfLifePanel);
        gameOfLifePanel.start();
    }

    private void start() {
        while (true) {
            ThreadUtils.sleep(1000);
            game = game.advanceTurn();
            this.repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Colors.Blues.AliceBlue);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        fillBoardWithHexagons(g, game);
    }

    private void fillBoardWithHexagons(Graphics g, GameOfLife game) {
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                if (Board.isValidCoordinate(x, y)) {
                    boolean isAlive = game.board.isAlive(new Cell(x,y));
                    paintHex(g, isAlive, new Coordinates(x,y));
                }
            }
        }
    }

    private void paintHex(Graphics g, boolean fill, Coordinates center) {
        Hexagon hexagon = new Hexagon(radius, center);

        if (fill) {
            g.setColor(Colors.Purples.MediumOrchid);
            g.fillPolygon(hexagon.getPolygon());
        }

        g.setColor(Color.BLACK);
        g.drawPolygon(hexagon.getPolygon());
    }

}
