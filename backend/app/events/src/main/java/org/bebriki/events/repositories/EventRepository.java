package org.bebriki.events.repositories;

import org.bebriki.events.models.EventEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.MonthDay;

public interface EventRepository extends CrudRepository<EventEntity, Long> {
    Iterable<EventEntity> findByEventDate(MonthDay eventDate);
}
