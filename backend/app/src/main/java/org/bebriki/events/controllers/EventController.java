package org.bebriki.events.controllers;

import lombok.RequiredArgsConstructor;
import org.bebriki.events.models.EventDto;
import org.bebriki.events.services.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.time.MonthDay;

@RequestMapping("api/v1/events")
@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping
    public Iterable<EventDto> findAll() {
        return eventService.findAll();
    }

    @GetMapping("/{id:[0-9]+}")
    public EventDto get(@PathVariable Long id) {
        return eventService.findById(id);
    }

    @GetMapping("/")
    public Iterable<EventDto> getByDate(@RequestParam int month, @RequestParam int day) {
        MonthDay monthDay = MonthDay.of(Month.of(month), day);
        return eventService.findByMonthDay(monthDay);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto create(@RequestBody EventDto eventDto) {
        return eventService.create(eventDto);
    }

    @PutMapping
    public EventDto update(@RequestBody EventDto eventDto) {
        return eventService.update(eventDto);
    }

    @DeleteMapping("/{id:[0-9]+}")
    public void delete(@PathVariable Long id) {
        eventService.delete(id);
    }

}
