///usr/bin/env jbang --java 25 "$0" "$@" ; exit $?
//SOURCES utils/*.java

import utils.Args;
import utils.EnvProxy;
import utils.Shell;

import static utils.Shell.run;
import static utils.TextFormat.bold;

void main(String... args) {
    try {
        // --- Load environment variables from .env.local ---
        var xcuser = EnvProxy.read().xcuser();
        var appArgs = new Args(args);

        var path = appArgs.path();

        IO.println("🚀 Starting App Store upload for " + bold(path.toString()));

        // --- Validation ---
        if (!appArgs.skipValidation()) {
            IO.println("IPA validation...");
            String validateCmd = String.format(
                    "xcrun altool --validate-app -f \"%s\" -t ios -u \"%s\" -p \"%s\" --output-format json", path,
                    xcuser.user(), xcuser.password());
            Shell.run(validateCmd);
        } else {
            System.out.println("⚠️ Skipping IPA validation step.");
        }

        // --- Upload ---
        String uploadCmd = String.format(
                "xcrun altool --upload-app -f \"%s\" -t ios -u \"%s\" -p \"%s\"--output-format json",
                path, xcuser.user(), xcuser.password());
        run(uploadCmd);

        System.out.println("\n🎉 Done! The app has been submitted to App Store Connect.");

    } catch (Exception e) {
        System.err.println("❌ Error: " + e);
    }
}
