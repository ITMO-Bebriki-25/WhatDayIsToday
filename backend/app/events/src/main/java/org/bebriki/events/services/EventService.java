package org.bebriki.events.services;

import org.bebriki.events.exceptions.EventNotFoundException;
import org.bebriki.events.models.EventDto;
import org.bebriki.events.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.MonthDay;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final ServiceModelMapper modelMapper;

    @Autowired
    public EventService(EventRepository eventRepository, ServiceModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
    }

    public Iterable<EventDto> findAll() {
        return modelMapper.toDtoIterable(eventRepository.findAll());
    }

    public Iterable<EventDto> findByMonthDay(MonthDay monthDay) {
        return modelMapper.toDtoIterable(eventRepository.findByEventDate(monthDay));
    }

    public EventDto findById(Long id) {
        return modelMapper.toDto(eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException("Event with id={" + id + "} not found")));
    }

    public EventDto create(EventDto eventDto) {
        eventDto.setId(null);
        return modelMapper.toDto(eventRepository.save(modelMapper.toEntity(eventDto)));
    }

    public EventDto update(EventDto eventDto) {
        eventRepository.findById(eventDto.getId()).orElseThrow(() -> new EventNotFoundException("Event with id={" + eventDto.getId() + "} not found"));
        return modelMapper.toDto(eventRepository.save(modelMapper.toEntity(eventDto)));
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }
}
