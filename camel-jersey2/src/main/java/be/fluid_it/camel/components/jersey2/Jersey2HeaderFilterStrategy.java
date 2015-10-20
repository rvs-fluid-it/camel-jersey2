package be.fluid_it.camel.components.jersey2;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultHeaderFilterStrategy;

/**
 * Default header filtering strategy for Jersey2
 *
 * @version
 */
public class Jersey2HeaderFilterStrategy extends DefaultHeaderFilterStrategy {

    public Jersey2HeaderFilterStrategy() {
        // No IN filters and copy all headers from Restlet to Camel

        // OUT filters (from Camel headers to Restlet headers)
        // filter headers used internally by this component
        getOutFilter().add(Jersey2Constants.JERSEY2_LOGIN);
        getOutFilter().add(Jersey2Constants.JERSEY2_PASSWORD);

        // The "CamelAcceptContentType" header is not added to the outgoing HTTP
        // headers but it will be going out as "Accept.
        getOutFilter().add(Exchange.ACCEPT_CONTENT_TYPE);

        // As we don't set the transfer_encoding protocol header for the restlet service
        // we need to remove the transfer_encoding which could let the client wait forever
        getOutFilter().add(Exchange.TRANSFER_ENCODING);
        setCaseInsensitive(true);
    }

}