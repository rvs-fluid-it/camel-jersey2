package be.fluid_it.camel.components.jersey2.showcase.service;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import javax.ws.rs.core.MediaType;

public class ShowcaseRoutes extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        rest("/say/hello").get().produces(MediaType.TEXT_HTML).route().transform().constant("Hello World From Route");
        rest("/people-json")
            .post().consumes(MediaType.APPLICATION_JSON).produces(MediaType.APPLICATION_JSON).type(Name.class)
            .route().id("post-json-route").log(LoggingLevel.INFO, "${body}");
        rest("/people-encoded").post().bindingMode(RestBindingMode.off).route().id("post-encoded-route").log(LoggingLevel.INFO, "${body}");
        restConfiguration().component("jersey2").bindingMode(RestBindingMode.json);
    }
}
