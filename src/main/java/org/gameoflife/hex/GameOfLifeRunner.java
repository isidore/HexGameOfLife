package org.gameoflife.hex;

import com.spun.swing.PaintablePanel;
import com.spun.swing.Paintables;
import com.spun.util.ThreadUtils;
import com.spun.util.WindowUtils;
import org.gameoflife.hex.game.HexGameOfLife;
import org.gameoflife.hex.graphics.MouseReleaseListener;
import org.gameoflife.hex.graphics.ResizeListener;

public class GameOfLifeRunner {
    public static void main(String[] args) {
        HexGameOfLife gameOfLife = setupInitialScenario();
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


    public static PaintablePanel<GameOfLifePanel> createGameOfLifePanel(HexGameOfLife gameOfLife) {

        GameOfLifePanel gameOfLifePanel = new GameOfLifePanel(gameOfLife);
        PaintablePanel<GameOfLifePanel> jPanel = Paintables.asPanel(gameOfLifePanel);
        jPanel.addMouseListener(new MouseReleaseListener(gameOfLifePanel));
        jPanel.addComponentListener(new ResizeListener(gameOfLifePanel));
        return jPanel;
    }

    public static HexGameOfLife setupInitialScenario() {
        HexGameOfLife gameOfLife = new HexGameOfLife();
        gameOfLife.setAlive(4, 4);
        gameOfLife.setAlive(3, 5);
        gameOfLife.setAlive(4, 6);
        gameOfLife.setAlive(5, 5);
        return gameOfLife;
    }

}
