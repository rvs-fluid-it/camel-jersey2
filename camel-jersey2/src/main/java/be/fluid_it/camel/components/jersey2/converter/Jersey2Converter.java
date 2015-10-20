package be.fluid_it.camel.components.jersey2.converter;

import org.apache.camel.Converter;

import javax.ws.rs.core.MediaType;

/**
 *
 * @version
 */
@Converter
public class Jersey2Converter {
    private Jersey2Converter() {
    }

    @Converter
    public static MediaType toMediaType(String name) {
        return MediaType.valueOf(name);
    }
}
