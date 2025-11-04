package utils;

import static utils.Shell.run;

import java.io.IOException;

import static utils.TextFormat.bold;

public final class GitProxy {
    private GitProxy() {
    }

    public static void assertCleanGit() throws IOException {
        if (!run("git status --porcelain").isBlank()) {
            throw new IOException("Working directory is not clean. Please commit or stash your changes first.");
        }
    }

    public enum CommitSource {
        ANDROID, IOS
    }

    public static void commit(CommitSource source, String version, String build) throws IOException {
        var tag = String.format("%s.%s.%s", source.toString().toLowerCase(), version, build);
        var commitMsg = tag;

        run("git add .");
        run(String.format("git commit -m '%s'", commitMsg));
        run(String.format("git tag '%s'", tag));
        run("git push");
        run(String.format("git push origin '%s'", tag));

        IO.println("Git committed & tagged as " + bold(tag));
    }
}
