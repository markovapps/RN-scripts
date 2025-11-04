///usr/bin/env jbang --java 25 "$0" "$@" ; exit $?
//SOURCES utils/*.java

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import utils.GitProxy.CommitSource;

import static utils.GitProxy.assertCleanGit;
import static utils.GitProxy.commit;
import static utils.TextFormat.bold;

String gradlePath = "android/app/build.gradle";

void main() {
    try {
        assertCleanGit();

        var gradleFile = Files.readString(Path.of(gradlePath));

        var versionName = getGradleValue(gradleFile, "versionName");
        var versionCode = getGradleValue(gradleFile, "versionCode");
        var nextVersionCode = String.valueOf(Integer.parseInt(versionCode) + 1);

        IO.println(String.format("v%s %s -> %s", versionName, bold(versionCode), bold(nextVersionCode)));

        // Update versionCode line
        gradleFile = gradleFile.replaceAll("versionCode\\s+\\d+", "versionCode " + nextVersionCode);
        Files.writeString(Path.of(gradlePath), gradleFile);

        commit(CommitSource.ANDROID, versionName, nextVersionCode);

    } catch (Exception e) {
        System.err.println("❌ Error: " + e);
        System.exit(1);
    }
}

String getGradleValue(String fileContent, String key) throws IOException {
    var regex = String.format("%s\\s+[\"']?([0-9A-Za-z.]+)[\"']?", key);
    var pattern = java.util.regex.Pattern.compile(regex);
    var matcher = pattern.matcher(fileContent);
    if (matcher.find()) {
        return matcher.group(1);
    }
    throw new IOException("Could not find " + key + " in " + gradlePath);
}
