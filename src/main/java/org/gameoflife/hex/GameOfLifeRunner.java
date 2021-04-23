package org.gameoflife.hex;

import com.spun.swing.Paintable;
import com.spun.util.ThreadUtils;
import com.spun.util.WindowUtils;

import javax.swing.*;
import java.awt.*;

public class GameOfLifeRunner {
    public static void main(String[] args) {
        GameOfLife gameOfLife = setupInitialScenario();
        PaintablePanel<GameOfLifePanel> gameOfLifePanel = createGameOfLifePanel(gameOfLife);
        startGame(gameOfLifePanel);
    }

    private static void startGame(PaintablePanel<GameOfLifePanel> gameOfLifePanel) {
        WindowUtils.testPanel(gameOfLifePanel);
        start(gameOfLifePanel.get());
    }

    public static class PaintablePanel<T extends Paintable> extends JPanel {
        private final T paintable;

        public PaintablePanel(T paintable) {
            this.paintable = paintable;
            this.setPreferredSize(paintable.getSize());
            ((GameOfLifePanel)paintable).registerRepaint(this::repaint);
        }

        public T get() {
            return paintable;
        }

        public void paint(Graphics g) {
            this.paintable.paint(g);
        }
    }


    public static <T extends Paintable> PaintablePanel<T> asPanel(T paintable) {
        return new PaintablePanel(paintable);
    }

    public static PaintablePanel<GameOfLifePanel> createGameOfLifePanel(GameOfLife gameOfLife) {

        GameOfLifePanel gameOfLifePanel = new GameOfLifePanel(gameOfLife);
        PaintablePanel<GameOfLifePanel> jPanel = asPanel(gameOfLifePanel);
        jPanel.addMouseListener(new MouseReleaseListener(gameOfLifePanel));
        jPanel.addComponentListener(new ResizeListener(gameOfLifePanel));
        return jPanel;
    }

    private static GameOfLife setupInitialScenario() {
        GameOfLife gameOfLife = new GameOfLife();
        gameOfLife.setAlive(4, 4);
        gameOfLife.setAlive(3, 5);
        gameOfLife.setAlive(4, 6);
        gameOfLife.setAlive(5, 5);
        return gameOfLife;
    }

    private static void start(GameOfLifePanel gameOfLifePanel) {
        while (true) {
            ThreadUtils.sleep(1000);
            gameOfLifePanel.advanceTurn();
        }
    }
}
