package utils;

import static utils.Shell.run;

import java.io.IOException;

public final class GitProxy {
    private GitProxy() {
    }

    public static void assertCleanGit() throws IOException {
        if (!run("git status --porcelain").isBlank()) {
            throw new IOException("Working directory is not clean. Please commit or stash your changes first.");
        }
    }
}
