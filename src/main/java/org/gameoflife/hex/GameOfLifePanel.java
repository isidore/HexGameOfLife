package org.gameoflife.hex;

import com.spun.swing.Paintable;
import com.spun.util.Colors;
import org.lambda.actions.Action0;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class GameOfLifePanel implements Paintable {
    private Dimension size;
    public int boardWidth = 20;
    public int boardHeight = 10;
    private final int radius = 20;
    private Action0 repaint = ()->{};

    public GameOfLife getGame() {
        return game;
    }

    private GameOfLife game;

    public GameOfLifePanel(GameOfLife game) {
        this.game = game;
        this.size = getPanelDimension(radius, boardWidth - 1, boardHeight - 1);
    }

    public GameOfLifePanel() {
        this(new GameOfLife());
    }

    void onResize(Dimension size) {
        this.size = size;
        Dimension d = getGridWidthAndHeightForPixels(radius, size);
        this.boardWidth = d.width;
        this.boardHeight = d.height;
    }

    public void advanceTurn() {
        this.game = game.advanceTurn();
        this.repaint();
    }

    public static Dimension getGridWidthAndHeightForPixels(int radius, Dimension sizeInPixel) {
        Point center = new Hexagon(radius, new Coordinates(0, 0)).getCenter();
        double hexWidth = center.x * 2;
        int boardWidth = (int) (sizeInPixel.width/hexWidth * 2);
        int boardHeight = (int)(sizeInPixel.height/(center.y * 2) * 1.5);

        return new Dimension(boardWidth, boardHeight);
    }

    public static Dimension getPanelDimension(int radius, int width, int height) {
        Hexagon bottomRightHexagon = new Hexagon(radius, new Coordinates(width, height));
        Rectangle boundingBox = bottomRightHexagon.getPolygon().getBounds();
        int rightmostX = boundingBox.x + boundingBox.width + 1;
        int lowestY = boundingBox.y + boundingBox.height + 1;

        return new Dimension(rightmostX, lowestY);
    }

    @Override
    public Dimension getSize() {
        return size;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Colors.Blues.AliceBlue);
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);
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

    public Optional<Coordinates> getGridCoordinatesAt(Point point) {
        Coordinates result = null;
        for (int x = 0; x < boardWidth; x++) {
            for (int y = 0; y < boardHeight; y++) {
                if (Board.isValidCoordinate(x, y)) {
                    Coordinates coordinates = new Coordinates(x, y);
                    Hexagon hexagon = new Hexagon(radius, coordinates);
                    if (hexagon.getPolygon().contains(point)) {
                        result = coordinates;
                        break;
                    }
                }
            }
            if (result != null) break;
        }
        return Optional.ofNullable(result);
    }

    public void setAliveAt(Point point) {
        Optional<Coordinates> cell = getGridCoordinatesAt(point);
        Coordinates hex = cell.get();
        game.setAlive(hex.getX(), hex.getY());
        repaint();
    }

    private void repaint() {
        repaint.call();
    }

    public void registerRepaint(Action0 repaint) {
        this.repaint = repaint;
    }
}
