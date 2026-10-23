package com.kshrd.hrdintelligence;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

/** Fails the build when a module uses another module's internals (anything outside its api/ packages). */
class ModularityTests {

    private final ApplicationModules modules = ApplicationModules.of(HrdIntelligenceApplication.class);

    @Test
    void verifiesModuleBoundaries() {
        modules.verify();
    }

    @Test
    void writesModuleDocumentation() {
        // Generates PlantUML module diagrams into build/spring-modulith-docs
        new Documenter(modules).writeModulesAsPlantUml().writeIndividualModulesAsPlantUml();
    }
}
