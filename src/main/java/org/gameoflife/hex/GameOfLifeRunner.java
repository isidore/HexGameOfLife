package org.gameoflife.hex;

import com.spun.swing.PaintablePanel;
import com.spun.swing.Paintables;
import com.spun.util.ThreadUtils;
import com.spun.util.WindowUtils;

public class GameOfLifeRunner {
    public static void main(String[] args) {
        GameOfLife gameOfLife = setupInitialScenario();
        PaintablePanel<GameOfLifePanel> gameOfLifePanel = createGameOfLifePanel(gameOfLife);
        startGame(gameOfLifePanel);
    }

    private static void startGame(PaintablePanel<GameOfLifePanel> gameOfLifePanel) {
        WindowUtils.testPanel(gameOfLifePanel);
        while (true) {
            ThreadUtils.sleep(1000);
            gameOfLifePanel.get().advanceTurn();
        }
    }



    public static PaintablePanel<GameOfLifePanel> createGameOfLifePanel(GameOfLife gameOfLife) {

        GameOfLifePanel gameOfLifePanel = new GameOfLifePanel(gameOfLife);
        PaintablePanel<GameOfLifePanel> jPanel = Paintables.asPanel(gameOfLifePanel);
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

}
