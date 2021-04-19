package org.gameoflife.hex;

import com.spun.util.Colors;
import com.spun.util.ThreadUtils;
import com.spun.util.WindowUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class GameOfLifePanel extends JPanel {
    public int boardWidth = 20;
    public int boardHeight = 10;
    private final int radius = 20;
    private GameOfLife game;

    public GameOfLifePanel(GameOfLife game) {
        this.game = game;
        this.setPreferredSize(getPanelDimension(radius, boardWidth - 1, boardHeight - 1));
    }

    public static void main(String[] args) {
        GameOfLife gameOfLife = setupInitialScenario();
        createPanelAndStartGame(getGameOfLifePanel(gameOfLife));
    }

    void onResize() {
        Dimension size = getSize();
        Dimension d = getGridWidthAndHeightForPixels(radius, size);
        this.boardWidth = d.width;
        this.boardHeight = d.height;
    }

    public static Dimension getGridWidthAndHeightForPixels(int radius, Dimension sizeInPixel) {
        Point center = new Hexagon(radius, new Coordinates(0, 0)).getCenter();
        double hexWidth = center.x * 2;
        int boardWidth = (int) (sizeInPixel.width/hexWidth * 2);
        int boardHeight = (int)(sizeInPixel.height/(center.y * 2) * 1.5);

        return new Dimension(boardWidth, boardHeight);
    }

    public GameOfLifePanel() {
        this(new GameOfLife());
    }

    public static Dimension getPanelDimension(int radius, int width, int height) {
        Hexagon bottomRightHexagon = new Hexagon(radius, new Coordinates(width, height));
        Rectangle boundingBox = bottomRightHexagon.getPolygon().getBounds();
        int rightmostX = boundingBox.x + boundingBox.width + 1;
        int lowestY = boundingBox.y + boundingBox.height + 1;

        return new Dimension(rightmostX, lowestY);
    }


    private static void createPanelAndStartGame(GameOfLifePanel gameOfLifePanel) {
        WindowUtils.testPanel(gameOfLifePanel);
        gameOfLifePanel.start();
    }

    private static GameOfLifePanel getGameOfLifePanel(GameOfLife gameOfLife) {
        GameOfLifePanel gameOfLifePanel = new GameOfLifePanel(gameOfLife);
        gameOfLifePanel.addMouseListener(new MouseReleaseListener(gameOfLifePanel));
        gameOfLifePanel.addComponentListener(new ResizeListener(gameOfLifePanel));
        return gameOfLifePanel;
    }

    private static GameOfLife setupInitialScenario() {
        GameOfLife gameOfLife = new GameOfLife();
        gameOfLife.setAlive(4, 4);
        gameOfLife.setAlive(3, 5);
        gameOfLife.setAlive(4, 6);
        gameOfLife.setAlive(5, 5);
        return gameOfLife;
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
}
