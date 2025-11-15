package utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.lang.ProcessBuilder.Redirect;

public final class Shell {

    private Shell() {
    } // prevent instantiation

    public record Result(int code, String result) {
    }

    public static Result runSafe(String cmd) throws IOException {
        var process = new ProcessBuilder("bash", "-c", cmd)
                .redirectErrorStream(true) // merge stdout + stderr
                .redirectOutput(Redirect.PIPE)
                .start();

        try (var in = process.getInputStream()) {
            var result = new String(in.readAllBytes(), StandardCharsets.UTF_8).trim();
            int exitCode = process.waitFor();

            return new Result(exitCode, result);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted while running: " + cmd, e);
        }
    }

    public static String run(String cmd) throws IOException {
        var result = runSafe(cmd);

        if (result.code != 0) {
            // Include the output in the exception to see what went wrong
            throw new IOException(
                    "Command failed with exit code " + result.code + ": " + cmd + "\nOutput:\n" + result.result);
        }
        return result.result;

    }
}
