package org.bebriki.gateway.application.events.services;

import org.bebriki.gateway.application.events.dto.CreateEventDto;
import org.bebriki.gateway.application.events.dto.EventDto;
import org.springframework.http.ResponseEntity;

import java.time.MonthDay;
import java.util.List;

public interface EventsService {
    ResponseEntity<EventDto> createEvent(CreateEventDto createEventDto);
    ResponseEntity<EventDto> updateEvent(EventDto eventDto);
    ResponseEntity<EventDto> getEventById(long id);
    ResponseEntity<List<EventDto>> getAllEvents();
    ResponseEntity<List<EventDto>> getEventByDate(MonthDay monthDay);
    ResponseEntity<Void> deleteEvent(long id);
}
