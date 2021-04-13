package org.gameoflife.hex;

import com.spun.util.Colors;
import com.spun.util.ThreadUtils;
import com.spun.util.WindowUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameOfLifePanel extends JPanel implements MouseListener {
    public static final int BOARD_WIDTH = 20;
    public static final int BOARD_HEIGHT = 10;
    private final int radius = 20;
    private GameOfLife game;

    public GameOfLifePanel(GameOfLife game) {
        this.game = game;
        this.setPreferredSize(getPanelDimension(radius, BOARD_WIDTH - 1, BOARD_HEIGHT - 1));
        this.addMouseListener(this);
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
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println(e.getPoint());
        Coordinates hex = getGridAt(e.getPoint());
        game.board.setAlive(hex.getX(), hex.getY());
        repaint();
    }

    public Coordinates getGridAt(Point point) {
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
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

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
