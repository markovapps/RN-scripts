package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class EnvProxy {
    private final Properties properties;

    private EnvProxy(Properties properties) {
        this.properties = properties;
    }

    public record XcUser(String user, String password) {
        public XcUser {
            if (user == null || password == null) {
                throw new IllegalArgumentException("XCRUN_USER and XCRUN_PASSWORD must not be null");
            }
        }
    }

    public XcUser xcuser() {
        return new XcUser(properties.getProperty("XCRUN_USER"), properties.getProperty("XCRUN_PASSWORD"));
    }

	record GpUser(String serviceAccountKeyPath, String projectId, String packageName) {
		public GpUser {
			if (serviceAccountKeyPath == null || projectId == null || packageName == null) {
				throw new IllegalArgumentException("serviceAccountKeyPath, projectId and packageName must not be null");
			}
		}
	}

	public GpUser gpuser() {
		return new GpUser(
				properties.getProperty("GP_SERVICE_ACCOUNT_KEY"),
				properties.getProperty("GP_PROJECT_ID"),
				properties.getProperty("GP_PACKAGE_NAME")
		);
	}

    public static EnvProxy read() throws IOException {
        return new EnvProxy(EnvProxy.readProps());
    }

    private static Properties readProps() throws IOException {

        Path envPath = Path.of(".env.local");
        if (!Files.exists(envPath)) {
            throw new IOException("Missing .env.local file");
        }

        Properties envProps = new Properties();
        try (var inputStream = Files.newInputStream(envPath)) {
            envProps.load(inputStream);
        }
        return envProps;
    }
}
