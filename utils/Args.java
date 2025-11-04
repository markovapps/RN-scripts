package utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Args {
    public static final String ARG_SKIP_VALIDATION = "--skip-validation";
    public static final String ARG_PATH = "--path";

    private final List<String> args;

    public Args(String... args) {
        if (args == null) {
            this.args = List.of();
        } else {
            this.args = Arrays.asList(args);
        }
    }

    public boolean skipValidation() {
        return args.contains(ARG_SKIP_VALIDATION);
    }

    public Path path() {
        var pathIndex = args.indexOf(ARG_PATH);
        if (pathIndex >= 0 && args.size() > pathIndex + 1) {
            var path = args.get(pathIndex + 1);

            Path resolvedPath = Path.of(path).toAbsolutePath();
            if (!Files.exists(resolvedPath)) {
                throw new InvalidParameterException("File not found:" + resolvedPath);
            }
            return resolvedPath;
        }

        throw new InvalidParameterException(ARG_PATH + " not provided");
    }
}
