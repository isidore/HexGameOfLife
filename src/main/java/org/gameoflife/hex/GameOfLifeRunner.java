package org.gameoflife.hex;

import com.spun.util.ThreadUtils;
import com.spun.util.WindowUtils;

public class GameOfLifeRunner {
    public static void main(String[] args) {
        GameOfLife gameOfLife = setupInitialScenario();
        GameOfLifePanel gameOfLifePanel = createGameOfLifePanel(gameOfLife);
        startGame(gameOfLifePanel);
    }

    private static void startGame(GameOfLifePanel gameOfLifePanel) {
        WindowUtils.testPanel(gameOfLifePanel);
        start(gameOfLifePanel);
    }

    public static GameOfLifePanel createGameOfLifePanel(GameOfLife gameOfLife) {
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

    private static void start(GameOfLifePanel gameOfLifePanel) {
        while (true) {
            ThreadUtils.sleep(1000);
            gameOfLifePanel.advanceTurn();
        }
    }
}
