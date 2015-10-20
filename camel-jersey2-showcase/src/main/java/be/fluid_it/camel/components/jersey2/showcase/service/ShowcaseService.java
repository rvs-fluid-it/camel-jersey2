package be.fluid_it.camel.components.jersey2.showcase.service;

import be.fluid_it.µs.bundle.dropwizard.µService;
import be.fluid_it.µs.bundle.dropwizard.µsBundle;
import be.fluid_it.µs.bundle.dropwizard.µsEnvironment;
import io.dropwizard.setup.Environment;

public class ShowcaseService extends µService<ShowcaseConfiguration> {
    static {
        µService.µServiceClass = ShowcaseService.class;
        µService.relativePathToYmlInIDE = "../camel-jersey2-showcase/src/main/config/showcase.yml";
    }

    @Override
    public Class<ShowcaseConfiguration> configurationClass() {
        return ShowcaseConfiguration.class;
    }

    @Override
    public void initialize(µsBundle.Builder<ShowcaseConfiguration> µsBundleBuilder) {
        µsBundleBuilder.addModule(new ShowcaseModule()).addRoutes(ShowcaseRoutes.class);
    }

    @Override
    protected void run(ShowcaseConfiguration configuration, Environment environment, µsEnvironment µsEnvironment) throws Exception {

    }
}
