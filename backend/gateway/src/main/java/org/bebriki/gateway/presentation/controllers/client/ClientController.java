package org.bebriki.gateway.presentation.controllers.client;

import lombok.RequiredArgsConstructor;
import org.bebriki.gateway.application.events.dto.CreateEventDto;
import org.bebriki.gateway.application.events.dto.EventDto;
import org.bebriki.gateway.application.events.services.EventsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.MonthDay;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ClientController {
    private final EventsService eventsService;

    @PostMapping("/events")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createEvent(@RequestBody CreateEventDto createEventDto) {
        return eventsService.createEvent(createEventDto);
    }

    @PutMapping("/events")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateEvent(@RequestBody EventDto eventDto) {
        return eventsService.updateEvent(eventDto);
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<?> getEventById(@PathVariable("id") long id) {
        return eventsService.getEventById(id);
    }

    @GetMapping(value = "/events", params = {"month", "day"})
    public ResponseEntity<?> getEventByDate(
            @RequestParam("month") int month,
            @RequestParam("day") int day)
    {
        MonthDay monthDay = MonthDay.of(month, day);
        return eventsService.getEventByDate(monthDay);
    }

    @DeleteMapping("/events/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") long id) {
        return eventsService.deleteEvent(id);
    }
}
