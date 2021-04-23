package org.gameoflife.hex;

import org.approvaltests.awt.AwtApprovals;
import org.approvaltests.reporters.FileCaptureReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

class GameOfLifeRunnerTest {

    @Test
    @UseReporter(FileCaptureReporter.class)
    void testGui() {
        GameOfLife game = GameOfLifeTest.createGameWithNeighbours(6, 6, true);
        AwtApprovals.verify(GameOfLifeRunner.createGameOfLifePanel(game));
    }
}