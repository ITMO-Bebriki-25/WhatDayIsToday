package org.bebriki.gateway.application.events.dto;

import lombok.Builder;
import lombok.Data;

import java.time.MonthDay;
import java.time.Year;

@Data
@Builder
public class CreateEventDto {
    private final String name;
    private final String description;
    private final String source;
    private final String imageUrl;
    private final Year eventYear;
    private final MonthDay eventDate;
}
