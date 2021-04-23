package org.gameoflife.hex;

import com.spun.util.ObjectUtils;
import com.spun.util.io.FileUtils;
import com.spun.util.logger.SimpleLogger;
import org.apache.commons.lang.StringUtils;
import org.approvaltests.reporters.FileCaptureReporter;

import java.io.IOException;
import java.util.Arrays;

public class MyFileCaptureReporter extends FileCaptureReporter {
    public MyFileCaptureReporter() {
        if(StringUtils.isNotEmpty(System.getenv("GITHUB_ACTIONS"))) {
            run("git", "config", "--local", "user.email", "action@github.com");
            run("git", "config", "--local", "user.name", "githubAction");
        }
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
