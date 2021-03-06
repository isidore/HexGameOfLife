package org.gameoflife.hex.graphics;

import org.approvaltests.awt.AwtApprovals;
import org.gameoflife.hex.game.HexGameOfLife;
import org.gameoflife.hex.game.HexGameOfLifeTest;
import org.junit.jupiter.api.Test;

class GameOfLifeRunnerTest {

    @Test
    void testGui() {
        HexGameOfLife game = HexGameOfLifeTest.createGameWithNeighbours(6, 6, true);
        AwtApprovals.verify(GameOfLifeRunner.createGameOfLifePanel(game).get());
    }
}