///usr/bin/env jbang --java 25 "$0" "$@" ; exit $?
//SOURCES utils/*.java

import utils.Args;
import utils.EnvProxy;

import static utils.Shell.run;
import static utils.TextFormat.bold;

void main(String... args) {
	try {
		// --- Load environment variables ---
		var env = EnvProxy.read();
		var gp = env.gpuser(); // expect: serviceAccountKeyPath(), packageName()
		var appArgs = new Args(args);
		var path = appArgs.path();

		IO.println("📱 Google Play package: " + bold(gp.packageName()));
		IO.println("🚀 Starting Play Store upload for " + bold(path.toString()));

		// --- Validation ---
		if (!appArgs.skipValidation()) {
			IO.println("✅ Validating AAB file...");
			run(String.format("bundletool validate --bundle=\"%s\"", path));
		} else {
			IO.println("⚠️ Skipping AAB validation step.");
		}

		// --- Authenticate with service account ---
		IO.println("🔐 Authenticating with Google...");
		run(String.format("gcloud auth activate-service-account --key-file=\"%s\"", gp.serviceAccountKeyPath()));

		// --- Upload to Google Play ---
		IO.println("📤 Uploading AAB to Google Play...");
		run(String.format(
				"gcloud --quiet --project=%s android-publisher bundles upload \"%s\" --package-name=%s",
				gp.projectId(), path, gp.packageName()
		));

		System.out.println("\n🎉 Done! The app has been uploaded to Google Play.");

	} catch (Exception e) {
		System.err.println("❌ Error: " + e.getMessage());
		e.printStackTrace();
	}
}
