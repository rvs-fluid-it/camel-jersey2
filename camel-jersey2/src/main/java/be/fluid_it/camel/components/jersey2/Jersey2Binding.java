package be.fluid_it.camel.components.jersey2;

import org.apache.camel.Exchange;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

/**
 * Interface for converting between Camel message and Jersey2 message.
 *
 * @version
 */
public interface Jersey2Binding {

    /**
     * Populate Jersey2 request message from Camel message
     *
     * @param exchange message to be copied from
     * @param responseBuilder to build a response
     * @throws Exception is thrown if error processing
     */
    void populateJersey2ResponseFromExchange(Exchange exchange, Response.ResponseBuilder responseBuilder) throws Exception;

    /**
     * Populate Camel message from Jersey2 request
     *
     *
     * @param request message to be copied from
     * @param responseBuilder to build a response
     * @param exchange to be populated  @throws Exception is thrown if error processing
     * @throws Exception is thrown if error processing
     */
    void populateExchangeFromJersey2RequestContext(ContainerRequestContext request, Response.ResponseBuilder responseBuilder, Exchange exchange) throws Exception;

    /**
     * Populate Jersey2 Request from Camel message
     *
     * @param request to be populated
     * @param exchange message to be copied from
     */
    void populateJersey2RequestContextFromExchange(ContainerRequestContext request, Exchange exchange);

    /**
     * Populate Camel message from Jersey2 response
     *
     * @param exchange to be populated
     * @param responseBuilder to build a response
     * @throws Exception is thrown if error processing
     */
    void populateExchangeFromJersey2Response(Exchange exchange, Response.ResponseBuilder responseBuilder) throws Exception;
}
