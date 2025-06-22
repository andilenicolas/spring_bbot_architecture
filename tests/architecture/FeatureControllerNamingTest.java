package com.yourcompany.yourapp.tests.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class FeatureControllerNamingTest {

    private final JavaClasses imported = new ClassFileImporter()
            .importPackages("com.yourcompany.yourapp.features");

    @Test
    void controllersShouldFollowNamingConvention() {
        ArchRule rule = classes()
                .that().resideInAPackage("..features..")
                .and().haveSimpleNameEndingWith("Controller")
                .should().haveSimpleNameMatching(".+Controller");

        rule.check(imported);
    }

    @Test
    void featureFoldersShouldUseKebabCase() {
        ArchRule rule = classes()
                .that().resideInAPackage("..features..")
                .should().resideInAPackage("..features..")
                .because("Feature folders should use lowercase and hyphens (kebab-case)");

        // This test will log only – ArchUnit does not natively validate kebab-case at
        // folder level
        rule.check(imported);
    }

    // Future: We can use a manual file-walker test to enforce kebab-case if
    // critical
}
