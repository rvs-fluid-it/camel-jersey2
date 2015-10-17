package be.fluid_it.camel.components;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class RestConfigurationTest  extends CamelTestSupport {
    static {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "info");
    }

    @Test
    public void testRestGet() {

    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                rest("/say/hello").get().route().transform().constant("Hello World");
                restConfiguration().component("jersey2");
            }
        };
    }

}
