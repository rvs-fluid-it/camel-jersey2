package be.fluid_it.camel.components.jersey2;

import java.util.Map;

import com.google.common.base.Strings;
import org.apache.camel.CamelContext;
import org.apache.camel.Consumer;
import org.apache.camel.Endpoint;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultComponent;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.spi.RestConfiguration;
import org.apache.camel.spi.RestConsumerFactory;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Represents the component that manages {@link Jersey2Endpoint}.
 */
public class Jersey2Component extends DefaultComponent implements RestConsumerFactory {

  private Logger logger = LoggerFactory.getLogger(getClass());

  protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
    Endpoint endpoint = new Jersey2Endpoint(uri, this);
    setProperties(endpoint, parameters);
    return endpoint;
  }

  @Override
  public Consumer createConsumer(final CamelContext camelContext, final Processor processor, String verb, String basePath, String uriTemplate, String consumes, String produces, RestConfiguration configuration, Map<String, Object> parameters) throws Exception {
    ResourceConfig resourceConfig = (ResourceConfig)camelContext.getRegistry().lookupByName(ResourceConfig.class.getName());
    final Resource.Builder resourceBuilder = Resource.builder();
    resourceBuilder.path(basePath);

    ResourceMethod.Builder methodBuilder = resourceBuilder.addMethod(verb.toUpperCase());
    if (!Strings.isNullOrEmpty(consumes)) {
      methodBuilder = methodBuilder.consumes(toMediaType(consumes));
    }
    methodBuilder.produces(toMediaType(produces))
            .handledBy(new Inflector<ContainerRequestContext, Response>() {
              @Override
              public Response apply(ContainerRequestContext containerRequestContext) {
                try {
                  DefaultJersey2Binding jersey2Binding = new DefaultJersey2Binding();
                  Jersey2Endpoint jersey2Endpoint = camelContext.getEndpoint("jersey2:provided", Jersey2Endpoint.class);
                  DefaultExchange exchange = new DefaultExchange(jersey2Endpoint);
                  Response.ResponseBuilder responseBuilder = Response.ok();
                  jersey2Binding.populateExchangeFromJersey2RequestContext(containerRequestContext, responseBuilder, exchange);
                  processor.process(exchange);
                  jersey2Binding.populateJersey2ResponseFromExchange(exchange, responseBuilder);
                  return responseBuilder.build();
                } catch (Exception e) {
                  e.printStackTrace();
                  Response.ResponseBuilder responseBuilder = Response.serverError();
                  return responseBuilder.entity(e).build();
                }
              }
            });
    Resource resource = resourceBuilder.build();
    resourceConfig.registerResources(resource);
    return null;
  }

  private MediaType toMediaType(String typeFromRestDSL) {
    if (Strings.isNullOrEmpty(typeFromRestDSL)) {
      return MediaType.TEXT_PLAIN_TYPE;
    } else {
      return MediaType.valueOf(typeFromRestDSL);
    }
  }
}
