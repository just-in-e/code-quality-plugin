package lt.ignitis.gpc.code.check;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.quality.CodeNarcExtension;
import org.gradle.api.plugins.quality.PmdExtension;
import org.gradle.api.plugins.quality.PmdPlugin;

public class CodeQualityPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {

        // Apply the PMD plugin
        project.getPlugins().apply(PmdPlugin.class);
        configurePmd(project);

        //Apply CodeNarc plugin
        configureCodeNarc(project);

    }

    private void configurePmd(Project project) {
        // Configure PMD
        project.getExtensions().configure(PmdExtension.class, pmd -> {
            pmd.setToolVersion("6.55.0"); // PMD version
            pmd.setRuleSetFiles(project.files("config/pmd/ruleset.xml")); // PMD ruleset
            pmd.setIgnoreFailures(false); // Fail the build on violations
        });

        // Ensure the PMD ruleset file is available
        project.afterEvaluate(proj -> {
            if (!proj.file("config/pmd/ruleset.xml").exists()) {
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
            codeNarc.setConfigFile(project.file("config/codenarc/codenarc-ruleset.xml")); // CodeNarc ruleset
            codeNarc.setMaxPriority1Violations(0); // Fail on priority 1 violations
            codeNarc.setMaxPriority2Violations(0); // Fail on priority 2 violations
        });

        // Ensure the CodeNarc ruleset file is available
        project.afterEvaluate(proj -> {
            if (!proj.file("config/codenarc/codenarc-ruleset.xml").exists()) {
                proj.copy(copySpec -> {
                    copySpec.from(getClass().getResourceAsStream("/codenarc-ruleset.xml"));
                    copySpec.into(proj.file("config/codenarc"));
                });
            }
        });
    }
}