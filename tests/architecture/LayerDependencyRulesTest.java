package com.yourcompany.yourapp.tests.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class LayerDependencyRulesTest {

    private final JavaClasses classes = new ClassFileImporter()
            .importPackages("com.yourcompany.yourapp");

    @Test
    void coreShouldNotDependOnOtherLayers() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..core..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..application..", "..features..", "..infrastructure..");

        rule.check(classes);
    }

    @Test
    void applicationShouldNotDependOnFeatures() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..application..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..features..");

        rule.check(classes);
    }

    @Test
    void featuresCanDependOnApplicationAndCore() {
        // Intentionally allowing features to use application services and core
        // utilities
        // No enforcement needed here unless you want to restrict
    }

    @Test
    void infrastructureShouldBeDependencyOnly() {
        ArchRule rule = noClasses()
                .that().resideInAnyPackage("..core..", "..application..", "..features..")
                .should().dependOnClassesThat()
                .resideInAPackage("..infrastructure..");

        rule.check(classes);
    }
}
