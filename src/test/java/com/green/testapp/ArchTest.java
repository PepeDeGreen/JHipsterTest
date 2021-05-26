package com.green.testapp;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.green.testapp");

        noClasses()
            .that()
            .resideInAnyPackage("com.green.testapp.service..")
            .or()
            .resideInAnyPackage("com.green.testapp.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.green.testapp.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
