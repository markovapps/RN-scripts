#!/usr/bin/env tsx
import { execSync } from "child_process";
import fs from "fs";

const gradlePath = "android/app/build.gradle"; // adjust if needed

function run(cmd: string): string {
    return execSync(cmd, { stdio: "pipe" }).toString().trim();
}

function assertCleanGit() {
    const status = run("git status --porcelain");
    if (status.length > 0) {
        console.error("❌ Working directory is not clean. Please commit or stash your changes first.");
        process.exit(1);
    }
}

function getGradleValue(file: string, key: string): string {
    const match = file.match(new RegExp(`${key}\\s+["']?([0-9A-Za-z.]+)["']?`));
    if (!match) throw new Error(`Could not find ${key} in build.gradle`);
    return match[1];
}

try {
    // 🚫 Stop if there are uncommitted changes
    assertCleanGit();

    const gradleFile = fs.readFileSync(gradlePath, "utf8");

    const versionName = getGradleValue(gradleFile, "versionName");
    const versionCode = parseInt(getGradleValue(gradleFile, "versionCode"), 10);
    const newVersionCode = versionCode + 1;

    // 🧮 Replace versionCode line
    const updatedGradle = gradleFile.replace(
        /versionCode\s+\d+/,
        `versionCode ${newVersionCode}`
    );

    fs.writeFileSync(gradlePath, updatedGradle);

    const tag = `android.${versionName}.${newVersionCode}`;
    const commitMsg = tag;

    run(`git add "${gradlePath}"`);
    run(`git commit -m "${commitMsg}"`);
    run(`git tag "${tag}"`);
    run(`git push`);
    run(`git push origin "${tag}"`);

    console.log(`✅ Updated versionCode to ${newVersionCode}, committed and tagged as ${tag}`);
} catch (e: any) {
    console.error("❌ Error:", e.message);
    process.exit(1);
}
