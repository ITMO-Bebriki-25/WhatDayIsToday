package org.bebriki.events.models.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.MonthDay;

@Converter(autoApply = true)
public class MonthDayAttributeConverter implements AttributeConverter<MonthDay, String> {

    @Override
    public String convertToDatabaseColumn(MonthDay attribute) {
        return (attribute != null) ? attribute.toString() : null;
    }

    @Override
    public MonthDay convertToEntityAttribute(String dbData) {
        return (dbData != null) ? MonthDay.parse(dbData) : null;
    }
}