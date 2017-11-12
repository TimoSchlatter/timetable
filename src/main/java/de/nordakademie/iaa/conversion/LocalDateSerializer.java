package de.nordakademie.iaa.conversion;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;

import static de.nordakademie.iaa.conversion.ConversionConfig.DATE_FORMATTER;

/**
 * Utility class for serializing {@code java.time.LocalDate}.
 *
 * @author Arvid Ottenberg
 */
public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.format(DATE_FORMATTER));
    }
}
