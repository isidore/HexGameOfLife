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
    @UseReporter(FileCaptureReporter.class)
    void testGui() {
        if(StringUtils.isNotEmpty(System.getenv("GITHUB_ACTIONS"))) {
            run("git", "config", "--local", "user.email", "action@github.com");
            run("git", "config", "--local", "user.name", "githubAction");
        }

        GameOfLife game = GameOfLifeTest.createGameWithNeighbours(6, 6, true);
        AwtApprovals.verify(GameOfLifeRunner.createGameOfLifePanel(game));
    }

    private void run(String... command) {
        SimpleLogger.event(Arrays.toString(command));

        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            int exitValue = process.exitValue();
            if (exitValue != 0) {
                String stdout = FileUtils.readStream(process.getInputStream());
                String stderr = FileUtils.readStream(process.getErrorStream());
                SimpleLogger.warning(String.format("stdout:\n%s\nstderr:\n%s", stdout, stderr));
            }

        } catch (InterruptedException | IOException var6) {
            throw ObjectUtils.throwAsError(var6);
        }
    }
}