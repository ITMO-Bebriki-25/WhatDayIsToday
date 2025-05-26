package org.bebriki.gateway.application.events.services;

import lombok.RequiredArgsConstructor;
import org.bebriki.gateway.application.events.clients.EventClient;
import org.bebriki.gateway.application.events.dto.CreateEventDto;
import org.bebriki.gateway.application.events.dto.EventDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.MonthDay;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventsServiceImpl implements EventsService {
    private final EventClient eventClient;

    @Override
    public ResponseEntity<EventDto> createEvent(CreateEventDto createEventDto) {
        return eventClient.createEvent(createEventDto);
    }

    @Override
    public ResponseEntity<EventDto> updateEvent(EventDto eventDto) {
        return eventClient.updateEvent(eventDto);
    }

    @Override
    public ResponseEntity<EventDto> getEventById(long id) {
        return eventClient.getEventById(id);
    }

    @Override
    public ResponseEntity<List<EventDto>> getAllEvents() {
        return eventClient.getAllEvents();
    }

    @Override
    public ResponseEntity<EventDto> getEventByDate(MonthDay monthDay) {
        return eventClient.getEventByDate(monthDay);
    }

    @Override
    public ResponseEntity<Void> deleteEvent(long id) {
        return eventClient.deleteEvent(id);
    }
}
