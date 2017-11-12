package de.nordakademie.iaa.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalTime;

import static de.nordakademie.iaa.Application.TIME_FORMATTER;

/**
 * Utility class for serializing {@code java.time.LocalTime}.
 *
 * @author Arvid Ottenberg
 */
public class LocalTimeSerializer extends JsonSerializer<LocalTime> {

    @Override
    public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.format(TIME_FORMATTER));
    }
}
