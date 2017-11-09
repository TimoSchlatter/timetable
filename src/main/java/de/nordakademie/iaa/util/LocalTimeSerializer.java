package de.nordakademie.iaa.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalTime;

import static de.nordakademie.iaa.Application.TIMEFORMATTER;

public class LocalTimeSerializer extends JsonSerializer<LocalTime> {

    @Override
    public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.format(TIMEFORMATTER));
    }
}
