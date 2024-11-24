package lt.ignitis.gpc.code.check.utility;

import org.gradle.api.Project;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class RulesetFileUtility {
    public static Path downloadRemoteRuleset(Project project, String remoteUrl, String rulesetFileName) {
        try {
            // Create a temporary file for the ruleset
            Path tempRulesetFile = project.getBuildDir().toPath().resolve(rulesetFileName);
            Files.createDirectories(tempRulesetFile.getParent());

            // Open connection to the remote URL
            URL url = new URL(remoteUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // Check for successful response
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException("Failed to download ruleset: HTTP " + connection.getResponseCode());
            }

            // Save the response content to a file
            try (InputStream inputStream = connection.getInputStream()) {
                Files.copy(inputStream, tempRulesetFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }

            return tempRulesetFile;
        } catch (Exception e) {
            throw new RuntimeException("Failed to download CodeNarc ruleset from remote source", e);
        }
    }

    public static Path copyRulesetToTemp(Project project, String rulesetFilename) {
        try {
            // Create a temporary file for the ruleset
            Path tempRulesetFile = project.getBuildDir().toPath().resolve(rulesetFilename);
            Files.createDirectories(tempRulesetFile.getParent());

            // Load the resource as a stream and copy it to the temporary location
            try (InputStream resourceStream = RulesetFileUtility.class.getResourceAsStream("/" + rulesetFilename)) {
                if (resourceStream == null) {
                    throw new IllegalStateException("CodeNarc ruleset resource not found!");
                }
                Files.copy(resourceStream, tempRulesetFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }

            return tempRulesetFile;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load CodeNarc ruleset configuration: " + e.getMessage(), e);
        }
    }
}
