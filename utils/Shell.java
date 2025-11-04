package utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.lang.ProcessBuilder.Redirect;

public final class Shell {

    private Shell() {
    } // prevent instantiation

    public static String run(String cmd) throws IOException {
        var process = new ProcessBuilder("bash", "-c", cmd)
                .redirectErrorStream(true) // merge stdout + stderr
                .redirectOutput(Redirect.PIPE)
                .start();

        try (var in = process.getInputStream()) {
            var result = new String(in.readAllBytes(), StandardCharsets.UTF_8).trim();
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                // Include the output in the exception to see what went wrong
                throw new IOException(
                        "Command failed with exit code " + exitCode + ": " + cmd + "\nOutput:\n" + result);
            }

            return result;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted while running: " + cmd, e);
        }
    }
}
