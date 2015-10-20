package be.fluid_it.camel.components.jersey2;

import org.apache.camel.Exchange;

import javax.ws.rs.container.ContainerRequestContext;

/**
 * Interface for converting between Camel message and Jersey2 message.
 *
 * @version
 */
public interface Jersey2Binding {

    // TODO String -> ContainerResponseContext

    /**
     * Populate Jersey2 request message from Camel message
     *
     * @param exchange message to be copied from
     * @param response to be populated
     * @throws Exception is thrown if error processing
     */
    void populateJersey2ResponseFromExchange(Exchange exchange, String response) throws Exception;

    /**
     * Populate Camel message from Jersey2 request
     *
     *
     * @param request message to be copied from
     * @param response the response
     * @param exchange to be populated  @throws Exception is thrown if error processing
     * @throws Exception is thrown if error processing
     */
    void populateExchangeFromJersey2Request(ContainerRequestContext request, String response, Exchange exchange) throws Exception;

    /**
     * Populate Jersey2 Request from Camel message
     *
     * @param request to be populated
     * @param exchange message to be copied from
     */
    void populateRestletRequestFromExchange(ContainerRequestContext request, Exchange exchange);

    /**
     * Populate Camel message from Jersey2 response
     *
     * @param exchange to be populated
     * @param response message to be copied from
     * @throws Exception is thrown if error processing
     */
    void populateExchangeFromRestletResponse(Exchange exchange, String response) throws Exception;
}
