package org.bebriki.gateway.presentation.controllers.client;

import lombok.RequiredArgsConstructor;
import org.bebriki.gateway.application.events.dto.CreateEventDto;
import org.bebriki.gateway.application.events.dto.EventDto;
import org.bebriki.gateway.application.events.services.EventsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.MonthDay;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ClientController {
    private final EventsService eventsService;

    @PostMapping("/events")
    public ResponseEntity<?> createEvent(@RequestBody CreateEventDto createEventDto) {
        return eventsService.createEvent(createEventDto);
    }

    @PutMapping("/events")
    public ResponseEntity<?> updateEvent(@RequestBody EventDto eventDto) {
        return eventsService.updateEvent(eventDto);
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<?> getEventById(@PathVariable("id") long id) {
        return eventsService.getEventById(id);
    }

    @GetMapping("/events")
    public ResponseEntity<?> getEventByDate(
            @RequestParam("month") int month,
            @RequestParam("day") int day)
    {
        MonthDay monthDay = MonthDay.of(month, day);
        return eventsService.getEventByDate(monthDay);
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") long id) {
        return eventsService.deleteEvent(id);
    }
}
