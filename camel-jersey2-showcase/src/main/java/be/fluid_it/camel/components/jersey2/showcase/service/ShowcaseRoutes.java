package be.fluid_it.camel.components.jersey2.showcase.service;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;

import javax.ws.rs.core.MediaType;

public class ShowcaseRoutes extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        rest("/say/hello").get().produces(MediaType.TEXT_HTML).route().transform().constant("Hello World From Route");
        restConfiguration().component("jersey2");
    }
}
