package de.nordakademie.iaa.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalTime;

import static de.nordakademie.iaa.Application.TIME_FORMATTER;

/**
 * Utility class for deserializing {@code java.time.LocalTime}.
 *
 * @author Arvid Ottenberg
 */
public class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {

    @Override
    public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return LocalTime.parse(p.getValueAsString(), TIME_FORMATTER);
    }
}
