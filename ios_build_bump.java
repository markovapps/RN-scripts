///usr/bin/env jbang --java 25 "$0" "$@" ; exit $?

import java.io.IOException;

import static utils.GitProxy.assertCleanGit;
import static utils.Shell.run;

String plistPath = "ios/Timesheet/Info.plist";

void main() {
    try {
        // assertCleanGit();

        var version = getValue("CFBundleShortVersionString");
        IO.println(version);
        // var build = Integer.parseInt(getValue("CFBundleVersion")) + 1;

        // setValue("CFBundleVersion", String.valueOf(build));

        // var tag = STR."ios.\{version}.\{build}";
        // var commitMsg = tag;

        // run(STR."git add '\{plistPath}'");
        // run(STR."git commit -m '\{commitMsg}'");
        // run(STR."git tag '\{tag}'");
        // run("git push");
        // run(STR."git push origin '\{tag}'");

        System.out.println("DDDD");
        // System.out.println(STR."✅ Updated build number to \{build}, committed and
        // tagged as \{tag}");
    } catch (Exception e) {
        System.err.println("❌ Error: " + e);
        System.exit(1);
    }
}

String getValue(String key) throws IOException {
    return run(String.format("/usr/libexec/PlistBuddy -c 'Print %s' '%s'", key, plistPath));
}

// void setValue(String key, String value) throws IOException {
// run(STR."/usr/libexec/PlistBuddy -c 'Set \{key} \{value}' '\{plistPath}'");
// }
