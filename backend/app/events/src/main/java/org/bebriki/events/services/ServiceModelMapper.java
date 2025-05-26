package org.bebriki.events.services;

import org.bebriki.events.models.EventDto;
import org.bebriki.events.models.EventEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ServiceModelMapper {
    private final ModelMapper modelMapper;

    public ServiceModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EventDto toDto(EventEntity event) {
        return modelMapper.map(event, EventDto.class);
    }

    public EventEntity toEntity(EventDto event) {
        return modelMapper.map(event, EventEntity.class);
    }

    public Iterable<EventDto> toDtoIterable(Iterable<EventEntity> eventEntities) {
        return StreamSupport.stream(eventEntities.spliterator(), false)
                .map(e -> modelMapper.map(e, EventDto.class))
                .collect(Collectors.toList());
    }
}
