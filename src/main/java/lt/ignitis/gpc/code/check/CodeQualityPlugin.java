package lt.ignitis.gpc.code.check;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.quality.PmdExtension;
import org.gradle.api.plugins.quality.PmdPlugin;

public class CodeQualityPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {

        // Apply the PMD plugin
        project.getPlugins().apply(PmdPlugin.class);

        // Configure PMD settings
        project.getExtensions().configure(PmdExtension.class, pmd -> {
            pmd.setToolVersion("6.55.0"); // PMD version
            pmd.setRuleSetFiles(project.files("config/pmd/pmd_ruleset.xml"));
            pmd.setIgnoreFailures(false);
        });

        // Add default PMD task if not already configured
        project.getTasks().named("pmdMain", task -> {
            task.doFirst(action -> {
                System.out.println("Running PMD check...");
            });
        });

        // Ensure rule file is copied into the project
        project.afterEvaluate(proj -> {
            proj.getTasks().register("copyPmdRules", task -> {
                task.doLast(action -> {
                    project.copy(spec -> {
                        spec.from(getClass().getResourceAsStream("/pmd_ruleset.xml"))
                                .into(project.file("config/pmd"));
                    });
                });
            });
        });
    }
}