package org.gameoflife.hex;

import com.spun.util.ObjectUtils;
import com.spun.util.io.FileUtils;
import com.spun.util.logger.SimpleLogger;
import org.apache.commons.lang.StringUtils;
import org.approvaltests.awt.AwtApprovals;
import org.approvaltests.reporters.FileCaptureReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

class GameOfLifeRunnerTest {

    @Test
    //@UseReporter(MyFileCaptureReporter.class)
    void testGui() {
        GameOfLife game = GameOfLifeTest.createGameWithNeighbours(6, 6, true);
        AwtApprovals.verify(GameOfLifeRunner.createGameOfLifePanel(game));
    }
}