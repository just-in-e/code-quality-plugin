package lt.ignitis.gpc.code.check;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.plugins.quality.CheckstyleExtension;
import org.gradle.api.plugins.quality.CheckstylePlugin;
import org.gradle.api.plugins.quality.CodeNarcExtension;
import org.gradle.api.plugins.quality.CodeNarcPlugin;
import org.gradle.api.plugins.quality.PmdExtension;

import java.nio.file.Path;

import static lt.ignitis.gpc.code.check.utility.RulesetFileUtility.copyRulesetToTemp;
import static lt.ignitis.gpc.code.check.utility.RulesetFileUtility.downloadRemoteRuleset;

public class CodeQualityPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {

//        // Apply the PMD plugin
//        project.getPlugins().apply(PmdPlugin.class);
//        configurePmd(project);

        // Apply Checkstyle plugin
        project.getPlugins().apply(CheckstylePlugin.class);
        configureCheckstyle(project);

        //Apply CodeNarc plugin
        project.getPlugins().apply(CodeNarcPlugin.class);
        configureCodeNarc(project);

    }

    private void configurePmd(Project project) {
        project.getExtensions().configure(PmdExtension.class, pmd -> {
            pmd.setToolVersion("6.55.0"); // PMD version
            pmd.setIgnoreFailures(false); // Fail the build on violations

            // Load ruleset from resources
            Path rulesetPath = copyRulesetToTemp(project, "pmd-ruleset.xml");
            FileCollection ruleSetFiles = project.files(rulesetPath.toFile());
            pmd.setRuleSetFiles(ruleSetFiles);

        });
    }

    private void configureCheckstyle(Project project) {
        project.getExtensions().configure(CheckstyleExtension.class, checkstyle -> {
            checkstyle.setToolVersion("10.20.1"); // Checkstyle version

            // Load ruleset from resources
            Path rulesetPath = copyRulesetToTemp(project, "checkstyle-ruleset.xml");
            checkstyle.setConfigFile(rulesetPath.toFile());
        });
    }

    private void configureCodeNarc(Project project) {
        project.getExtensions().configure(CodeNarcExtension.class, codeNarc -> {
            codeNarc.setToolVersion("3.3.0"); // CodeNarc version
            codeNarc.setReportFormat("console");
            codeNarc.setMaxPriority1Violations(0); // Fail on priority 1 violations
            codeNarc.setMaxPriority2Violations(0); // Fail on priority 2 violations
            codeNarc.setMaxPriority3Violations(500); // Fail on priority 3 violations

            // Load the remote ruleset
//            Path rulesetPath = downloadRemoteRuleset(project, "https://raw.githubusercontent.com/just-in-e/code-quality-rules/refs/heads/main/codenarc-ruleset.xml", "codenarc-ruleset.xml");

            // Load ruleset from resources
            Path rulesetPath = copyRulesetToTemp(project, "codenarc-ruleset.xml");
            codeNarc.setConfigFile(rulesetPath.toFile());
        });
    }

}