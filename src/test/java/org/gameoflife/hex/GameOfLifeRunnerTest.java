package org.gameoflife.hex;

import org.approvaltests.awt.AwtApprovals;
import org.gameoflife.hex.game.HexGameOfLife;
import org.gameoflife.hex.game.HexGameOfLifeTest;
import org.gameoflife.hex.graphics.GameOfLifeRunner;
import org.junit.jupiter.api.Test;

class GameOfLifeRunnerTest {

    @Test
    void testGui() {
        HexGameOfLife game = HexGameOfLifeTest.createGameWithNeighbours(6, 6, true);
        AwtApprovals.verify(GameOfLifeRunner.createGameOfLifePanel(game).get());
    }
}