#!/usr/bin/env ts-node

import { execSync } from "child_process";
import { config } from "dotenv";
import path from "path";
import fs from "fs";

// Load environment variables from .env.local
config({ path: ".env.local" });

// --- Parse CLI arguments ---
const args = process.argv.slice(2);
const skipValidation = args.includes("--skip-validation");

const pathIndex = args.indexOf("--path");
const ipaPath = pathIndex !== -1 ? args[pathIndex + 1] : null;

if (!ipaPath) {
  console.error("❌ Missing IPA file path.\nUsage: npm run ios:submit -- --path /path/to/app.ipa [--skip-validation]");
  process.exit(1);
}

const resolvedPath = path.resolve(ipaPath);
if (!fs.existsSync(resolvedPath)) {
  console.error(`❌ File not found: ${resolvedPath}`);
  process.exit(1);
}

const user = process.env.XCRUN_USER;
const password = process.env.XCRUN_PASSWORD;

if (!user || !password) {
  console.error("❌ Missing XCRUN_USER or XCRUN_PASSWORD in .env.local");
  process.exit(1);
}

function runCommand(cmd: string, description: string) {
  console.log(`\n▶️  ${description}...`);
  try {
    execSync(cmd, { stdio: "inherit" });
    console.log(`✅ ${description} completed successfully.`);
    return true;
  } catch (error: any) {
    console.error(`❌ ${description} failed.`);
    console.error(error)
    return false;
  }
}

console.log(`🚀 Starting App Store upload for ${resolvedPath}\n`);

if (!skipValidation) {
  const validateCmd = `xcrun altool --validate-app -f "${resolvedPath}" -t ios -u "${user}" -p "${password}" --output-format json`;
  const validated = runCommand(validateCmd, "Validating IPA");
  if (!validated) process.exit(1);
} else {
  console.log("⚠️  Skipping IPA validation step.");
}

const uploadCmd = `xcrun altool --upload-app -f "${resolvedPath}" -t ios -u "${user}" -p "${password}" --output-format json`;
const uploaded = runCommand(uploadCmd, "Uploading IPA");
if (!uploaded) process.exit(1);

console.log("\n🎉 Done! The app has been submitted to App Store Connect.");
