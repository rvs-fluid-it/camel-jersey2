package be.fluid_it.camel.components.jersey2.showcase.service;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;

public class ShowcaseRoutes extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        rest("/say/hello").get().route().transform().constant("Hello World");
        restConfiguration().component("jersey2");
    }
}
