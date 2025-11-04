///usr/bin/env jbang --java 25 "$0" "$@" ; exit $?
//SOURCES utils/**.java

import java.io.IOException;

import utils.GitProxy.CommitSource;

import static utils.GitProxy.assertCleanGit;
import static utils.GitProxy.commit;
import static utils.Shell.run;
import static utils.TextFormat.bold;

String plistPath = "ios/Timesheet/Info.plist";

void main() {
    try {
        assertCleanGit();

        var version = getValue("CFBundleShortVersionString");
        var build = getValue("CFBundleVersion");
        var nextBuild = String.valueOf(Integer.parseInt(build) + 1);

        IO.println(String.format("v%s %s -> %s", version, bold(build), bold(nextBuild)));

        setValue("CFBundleVersion", nextBuild);

        commit(CommitSource.IOS, version, nextBuild);
    } catch (Exception e) {
        System.err.println("❌ Error: " + e);
        System.exit(1);
    }
}

String getValue(String key) throws IOException {
    return run(String.format("/usr/libexec/PlistBuddy -c 'Print %s' '%s'", key, plistPath));
}

void setValue(String key, String value) throws IOException {
    run(String.format("/usr/libexec/PlistBuddy -c 'Set %s %s' '%s'", key, value, plistPath));
}
