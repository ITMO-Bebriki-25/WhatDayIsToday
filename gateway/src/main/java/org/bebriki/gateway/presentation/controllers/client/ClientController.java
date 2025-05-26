package org.bebriki.gateway.presentation.controllers.client;

import lombok.RequiredArgsConstructor;
import org.bebriki.gateway.application.events.dto.CreateEventDto;
import org.bebriki.gateway.application.events.dto.EventDto;
import org.bebriki.gateway.application.events.services.EventsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.MonthDay;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ClientController {
    private final EventsService eventsService;

    @PostMapping("/events")
    public ResponseEntity<?> createEvent(CreateEventDto createEventDto) {
        return eventsService.createEvent(createEventDto);
    }

    public ResponseEntity<?> updateEvent(EventDto eventDto) {
        return eventsService.updateEvent(eventDto);
    }

    public ResponseEntity<?> getEventById(long id) {
        return eventsService.getEventById(id);
    }

    public ResponseEntity<?> getEventByDate(MonthDay monthDay) {
        return eventsService.getEventByDate(monthDay);
    }

    public ResponseEntity<?> deleteEvent(long id) {
        return eventsService.deleteEvent(id);
    }
}
