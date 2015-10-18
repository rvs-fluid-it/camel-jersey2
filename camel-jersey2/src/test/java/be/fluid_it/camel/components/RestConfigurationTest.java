package be.fluid_it.camel.components;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;

public class RestConfigurationTest  extends CamelTestSupport {
    static {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "info");
    }

    @Test
    public void testRestGet() {

    }

    @Override
    protected JndiRegistry createRegistry() throws Exception {
        JndiRegistry jndiRegistry = super.createRegistry();
        jndiRegistry.bind(ResourceConfig.class.getName(), new ResourceConfig());
        return jndiRegistry;
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
