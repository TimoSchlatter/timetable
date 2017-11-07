package de.nordakademie.iaa.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Time;
import java.time.LocalTime;

@Converter(autoApply = true)
public class LocalTimeConverter implements AttributeConverter<LocalTime, Time> {

    @Override
    public Time convertToDatabaseColumn(LocalTime locDateTime) {
        return (locDateTime == null ? null : Time.valueOf(locDateTime));
    }

    @Override
    public LocalTime convertToEntityAttribute(Time sqlTimestamp) {
        return (sqlTimestamp == null ? null : sqlTimestamp.toLocalTime());
    }
}
