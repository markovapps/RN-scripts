#!/usr/bin/env tsx
import { execSync } from "child_process";

const plistPath = "ios/Timesheet/Info.plist"; // adjust if needed

function run(cmd: string): string {
  return execSync(cmd, { stdio: "pipe" }).toString().trim();
}

function getValue(key: string): string {
  return run(`/usr/libexec/PlistBuddy -c "Print ${key}" "${plistPath}"`);
}

function setValue(key: string, value: string | number) {
  run(`/usr/libexec/PlistBuddy -c "Set ${key} ${value}" "${plistPath}"`);
}

function assertCleanGit() {
  const status = run("git status --porcelain");
  if (status.length > 0) {
    console.error("❌ Working directory is not clean. Please commit or stash your changes first.");
    process.exit(1);
  }
}

try {
  // 🚫 Stop if there are uncommitted changes
  assertCleanGit();

  const version = getValue("CFBundleShortVersionString");
  const build = parseInt(getValue("CFBundleVersion"), 10) + 1;

  setValue("CFBundleVersion", build);

  const tag = `ios.${version}.${build}`;
  const commitMsg = tag;

  run(`git add "${plistPath}"`);
  run(`git commit -m "${commitMsg}"`);
  run(`git tag "${tag}"`);

  console.log(`✅ Updated build number to ${build}, committed and tagged as ${tag}`);
} catch (e: any) {
  console.error("❌ Error:", e.message);
  process.exit(1);
}
