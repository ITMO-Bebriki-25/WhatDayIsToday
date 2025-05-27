package org.bebriki.gateway.application.events.dto;

import lombok.Builder;
import lombok.Data;

import java.time.MonthDay;
import java.time.Year;

@Data
@Builder
public class EventDto {
    private final long id;
    private final String name;
    private final String description;
    private final String sourceUrl;
    private final String imageUrl;
    private final Year eventYear;
    private final MonthDay eventDate;
}