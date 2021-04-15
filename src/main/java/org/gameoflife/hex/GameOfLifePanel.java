package org.gameoflife.hex;

import com.spun.util.Colors;
import com.spun.util.ThreadUtils;
import com.spun.util.WindowUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameOfLifePanel extends JPanel {
    public int boardWidth = 20;
    public int boardHeight = 10;
    private final int radius = 20;
    private GameOfLife game;

    public GameOfLifePanel(GameOfLife game) {
        this.game = game;
        this.setPreferredSize(getPanelDimension(radius, boardWidth - 1, boardHeight - 1));
        this.addMouseListener(new MouseReleaseListener(this));
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                onResize();
            }
        });
    }

    private void onResize() {
        System.out.println(getSize());
    }

    public GameOfLifePanel() {
        this(new GameOfLife());
    }

    private static Dimension getPanelDimension(int radius, int width, int height) {
        Hexagon bottomRightHexagon = new Hexagon(radius, new Coordinates(width, height));
        Rectangle boundingBox = bottomRightHexagon.getPolygon().getBounds();
        int rightmostX = boundingBox.x + boundingBox.width + 1;
        int lowestY = boundingBox.y + boundingBox.height + 1;

        return new Dimension(rightmostX, lowestY);
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
        for (int x = 0; x < boardWidth; x++) {
            for (int y = 0; y < boardHeight; y++) {
                if (Board.isValidCoordinate(x, y)) {
                    boolean isAlive = game.board.isAlive(new Cell(x, y));
                    paintHex(g, isAlive, new Coordinates(x, y));
                }
            }
        }
    }

    private void paintHex(Graphics g, boolean fill, Coordinates coordinates) {
        Hexagon hexagon = new Hexagon(radius, coordinates);

        if (fill) {
            g.setColor(Colors.Purples.MediumOrchid);
            g.fillPolygon(hexagon.getPolygon());
        }

        g.setColor(Color.BLACK);
        g.drawPolygon(hexagon.getPolygon());
    }

    public Coordinates getGridAt(Point point) {
        for (int x = 0; x < boardWidth; x++) {
            for (int y = 0; y < boardHeight; y++) {
                if (Board.isValidCoordinate(x, y)) {
                    Coordinates coordinates = new Coordinates(x, y);
                    Hexagon hexagon = new Hexagon(radius, coordinates);
                    if (hexagon.getPolygon().contains(point)) {
                        return coordinates;
                    }
                }
            }
        }
        return null;
    }

    public void setAliveAt(Point point) {
        Coordinates hex = getGridAt(point);
        game.board.setAlive(hex.getX(), hex.getY());
        repaint();
    }


}
