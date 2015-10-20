package be.fluid_it.camel.components.jersey2;

import org.apache.camel.CamelException;

import java.io.Serializable;
import java.util.Map;

public class Jersey2OperationException  extends CamelException implements Serializable {

    private final String uri;
    private final String redirectLocation;
    private final int statusCode;
    private final String statusText;
    private final Map<String, String> responseHeaders;
    private final String responseBody;

    public Jersey2OperationException(String uri, int statusCode, String statusText, String location, Map<String, String> responseHeaders,
                                     String responseBody) {
        super("Restlet operation failed invoking " + uri + " with statusCode: " + statusCode + (location != null ? ", redirectLocation: " + location
                : "" + " /n responseBody:" + responseBody));
        this.uri = uri;
        this.statusCode = statusCode;
        this.statusText = statusText;
        this.redirectLocation = location;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }

    public String getRedirectLocation() {
        return redirectLocation;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public Map<String, String> getResponseHeaders() {
        return responseHeaders;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public String getUri() {
        return uri;
    }
}