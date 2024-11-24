package lt.ignitis.gpc.code.check;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.quality.CodeNarcExtension;
import org.gradle.api.plugins.quality.CodeNarcPlugin;
import org.gradle.api.plugins.quality.PmdExtension;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import static lt.ignitis.gpc.code.check.utility.RulesetFileUtility.copyRulesetToTemp;
import static lt.ignitis.gpc.code.check.utility.RulesetFileUtility.downloadRemoteRuleset;

public class CodeQualityPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {

//        // Apply the PMD plugin
//        project.getPlugins().apply(PmdPlugin.class);
//        configurePmd(project);

        //Apply CodeNarc plugin
        project.getPlugins().apply(CodeNarcPlugin.class);
        configureCodeNarc(project);

    }

    private void configurePmd(Project project) {
        // Configure PMD
        project.getExtensions().configure(PmdExtension.class, pmd -> {
            pmd.setToolVersion("6.55.0"); // PMD version
            pmd.setRuleSetFiles(project.files("config/pmd/pmd-ruleset.xml")); // PMD ruleset
            pmd.setIgnoreFailures(false); // Fail the build on violations
        });

        // Ensure the PMD ruleset file is available
        project.afterEvaluate(proj -> {
            if (!proj.file("config/pmd/pmd-ruleset.xml").exists()) {
                proj.copy(copySpec -> {
                    copySpec.from(getClass().getResourceAsStream("/pmd-ruleset.xml"));
                    copySpec.into(proj.file("config/pmd"));
                });
            }
        });
    }

    private void configureCodeNarc(Project project) {
        // Configure CodeNarc
        project.getExtensions().configure(CodeNarcExtension.class, codeNarc -> {
            codeNarc.setToolVersion("3.3.0"); // CodeNarc version
            codeNarc.setReportFormat("console");
            codeNarc.setMaxPriority1Violations(0); // Fail on priority 1 violations
            codeNarc.setMaxPriority2Violations(0); // Fail on priority 2 violations

            // Load the remote ruleset
//            Path rulesetPath = downloadRemoteRuleset(project, "https://raw.githubusercontent.com/just-in-e/code-quality-rules/refs/heads/main/codenarc-ruleset.xml", "codenarc-ruleset.xml");
//            codeNarc.setConfigFile(rulesetPath.toFile());

            // Load ruleset from resources
            Path rulesetPath = copyRulesetToTemp(project, "codenarc-ruleset.xml");
            codeNarc.setConfigFile(rulesetPath.toFile());
        });
    }

}