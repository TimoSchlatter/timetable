package de.nordakademie.iaa.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Time;
import java.time.LocalTime;

/**
 * Utility class for converting {@code java.time.LocalDate} to {@code java.util.Time} and vice versa.
 *
 * @author Arvid Ottenberg
 */
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
