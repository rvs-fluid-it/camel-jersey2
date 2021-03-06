package be.fluid_it.camel.components.jersey2;

import org.apache.camel.Exchange;
import org.apache.camel.spi.HeaderFilterStrategy;
import org.apache.camel.spi.HeaderFilterStrategyAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Default Jersey2 binding implementation
 */
public class DefaultJersey2Binding implements Jersey2Binding, HeaderFilterStrategyAware {
  private static final Logger logger = LoggerFactory.getLogger(DefaultJersey2Binding.class);
  private static final String RFC_2822_DATE_PATTERN = "EEE, dd MMM yyyy HH:mm:ss Z";
  private HeaderFilterStrategy headerFilterStrategy;

  @Override
  public void populateJersey2ResponseFromExchange(Exchange exchange, Response.ResponseBuilder responseBuilder) throws Exception {
    responseBuilder.status(Response.Status.OK).entity(exchange.getIn().getBody());
    setHttpHeaders(exchange, responseBuilder);
  }

  @Override
  public void populateExchangeFromJersey2RequestContext(ContainerRequestContext requestContext, Response.ResponseBuilder responseBuilder, Exchange exchange) throws Exception {
    exchange.getIn().setBody(requestContext.getEntityStream());
    setQueryParameters(requestContext, exchange);
  }

  @Override
  public void populateJersey2RequestContextFromExchange(ContainerRequestContext request, Exchange exchange) {
    throw new UnsupportedOperationException("Not yet implemented ...");
  }

  @Override
  public void populateExchangeFromJersey2Response(Exchange exchange, Response.ResponseBuilder responseBuilder) throws Exception {
    throw new UnsupportedOperationException("Not yet implemented ...");
  }

  @SuppressWarnings("unchecked")
  protected boolean setResponseHeader(Exchange exchange, Response.ResponseBuilder responseBuilder, String header, Object value) {
    // there must be a value going forward
    if (value == null) {
      return true;
    }
    if (header.equalsIgnoreCase(HttpHeaders.CACHE_CONTROL)) {
      if (value instanceof List) {
        // TODO
      }
      if (value instanceof String) {
        responseBuilder.cacheControl(CacheControl.valueOf(value.toString()));
      }
      return true;
    } else if (header.equalsIgnoreCase(HttpHeaders.EXPIRES)) {
      if (value instanceof Calendar) {
        responseBuilder.expires(((Calendar) value).getTime());
      } else if (value instanceof Date) {
        responseBuilder.expires((Date) value);
      } else if (value instanceof String) {
        SimpleDateFormat format = new SimpleDateFormat(RFC_2822_DATE_PATTERN, Locale.ENGLISH);
        try {
          Date date = format.parse((String) value);
          responseBuilder.expires(date);
        } catch (ParseException e) {
          logger.debug("Header {} with value {} cannot be converted as a Date. The value will be ignored.", HttpHeaders.EXPIRES, value);
        }
      }
      return true;
    } else if (header.equalsIgnoreCase(HttpHeaders.LAST_MODIFIED)) {
      if (value instanceof Calendar) {
        responseBuilder.lastModified(((Calendar) value).getTime());
      } else if (value instanceof Date) {
        responseBuilder.lastModified((Date) value);
      } else if (value instanceof String) {
        SimpleDateFormat format = new SimpleDateFormat(RFC_2822_DATE_PATTERN, Locale.ENGLISH);
        try {
          Date date = format.parse((String) value);
          responseBuilder.lastModified(date);
        } catch (ParseException e) {
          logger.debug("Header {} with value {} cannot be converted as a Date. The value will be ignored.", HttpHeaders.LAST_MODIFIED, value);
        }
      }
      return true;
    } else if (header.equalsIgnoreCase(HttpHeaders.CONTENT_LENGTH)) {
      if (value instanceof Long) {
      } else if (value instanceof Integer) {
      } else {
        Long num = exchange.getContext().getTypeConverter().tryConvertTo(Long.class, value);
        if (num != null) {
        } else {
          logger.debug("Header {} with value {} cannot be converted as a Long. The value will be ignored.", HttpHeaders.CONTENT_LENGTH, value);
        }
      }
      return true;
    } else if (header.equalsIgnoreCase(HttpHeaders.CONTENT_TYPE)) {
      if (value instanceof MediaType) {
        responseBuilder.header(HttpHeaders.CONTENT_TYPE, value.toString());
      } else {
        String type = value.toString();
        MediaType media = MediaType.valueOf(type);
        if (media != null) {
          responseBuilder.header(HttpHeaders.CONTENT_TYPE, type);
        } else {
          logger.debug("Header {} with value {} cannot be converted as a MediaType. The value will be ignored.", HttpHeaders.CONTENT_TYPE, value);
        }
      }
      return true;
    } else {
      responseBuilder.header(header, value);
    }
    return false;
  }

  @Override
  public HeaderFilterStrategy getHeaderFilterStrategy() {
    return headerFilterStrategy;
  }

  @Override
  public void setHeaderFilterStrategy(HeaderFilterStrategy strategy) {
    headerFilterStrategy = strategy;
  }

  private void setQueryParameters(ContainerRequestContext requestContext, Exchange exchange) {
    final MultivaluedMap<String, String> queryParameters = requestContext.getUriInfo().getQueryParameters();
    if (queryParameters != null && queryParameters.size() > 0) {
      final Map<String, String> map = new HashMap<>();
      for (MultivaluedMap.Entry<String, List<String>> entry : queryParameters.entrySet()) {
        map.put(entry.getKey(), (entry.getValue() != null && entry.getValue().size() > 0 ? entry.getValue().get(0) : null));
      }
      exchange.getIn().setHeader(Exchange.HTTP_QUERY, map);
    }
  }

  private void setHttpHeaders(Exchange exchange, Response.ResponseBuilder responseBuilder) {
    final String contentType = (String) exchange.getIn().getHeader(Exchange.CONTENT_TYPE);
    if (contentType != null) {
      responseBuilder.header(HttpHeaders.CONTENT_TYPE, contentType);
    }
    final String fileName = (String) exchange.getIn().getHeader(Exchange.FILE_NAME);
    if (fileName != null) {
      responseBuilder.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"");
    }
    final Boolean cache = exchange.getIn().getHeader(Exchange.DISABLE_HTTP_STREAM_CACHE, Boolean.class);
    if (cache == null || cache) {
      final CacheControl cacheControl = new CacheControl();
      cacheControl.setNoCache(true);
      responseBuilder.cacheControl(cacheControl);
    }
  }

}
