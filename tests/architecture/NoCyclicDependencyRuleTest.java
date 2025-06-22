package com.yourcompany.yourapp.tests.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.Test;

public class NoCyclicDependencyRuleTest {

    private final JavaClasses classes = new ClassFileImporter()
            .importPackages("com.yourcompany.yourapp");

    @Test
    void noCyclicDependenciesBetweenPackages() {
        SlicesRuleDefinition.slices()
                .matching("com.yourcompany.yourapp.(*)..")
                .should().beFreeOfCycles()
                .check(classes);
    }
}
