package org.bebriki.events.models;

import lombok.Data;

import java.time.MonthDay;
import java.time.Year;

@Data
public class EventDto {
    private Long id;

    private String name;

    private String description;

    private String sourceUrl;

    private String imageUrl;

    private Year eventYear;

    private MonthDay eventDate;
}
