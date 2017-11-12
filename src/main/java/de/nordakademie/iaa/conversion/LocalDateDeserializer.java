package de.nordakademie.iaa.conversion;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;

import static de.nordakademie.iaa.conversion.ConversionConfig.DATE_FORMATTER;

/**
 * Utility class for deserializing {@code java.time.LocalDate}.
 *
 * @author Arvid Ottenberg
 */
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return LocalDate.parse(p.getValueAsString(), DATE_FORMATTER);
    }
}
