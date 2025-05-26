package org.bebriki.gateway.application.events.clients;

import lombok.RequiredArgsConstructor;
import org.bebriki.gateway.application.events.dto.CreateEventDto;
import org.bebriki.gateway.application.events.dto.EventDto;
import org.bebriki.gateway.application.proxy.ProxyRestService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.MonthDay;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EventClientImpl implements EventClient{
    private final RestClient restClient;
    private final ProxyRestService proxyRestService;
    private final RestClient.Builder builder;

    // POST api/v1/events/eventDtoBody
    @Override
    public ResponseEntity<EventDto> createEvent(CreateEventDto createEventDto) {
        return proxyRestService.proxyRequest(
                () -> restClient.post()
                        .uri(builder -> builder
                                .path("/events")
                                .build())
                        .body(createEventDto)
                        .retrieve(), EventDto.class
        );
    }

    // PUT api/v1/events/eventDtoBody
    @Override
    public ResponseEntity<EventDto> updateEvent(EventDto eventDto) {
        return proxyRestService.proxyRequest(
                () -> restClient.put()
                        .uri(builder -> builder
                                .path("/events")
                                .build())
                        .body(eventDto)
                        .retrieve(), EventDto.class
        );
    }

    // GET api/v1/events/{id}
    @Override
    public ResponseEntity<EventDto> getEventById(long id) {
        return proxyRestService.proxyRequest(
                () -> restClient.get()
                        .uri(builder -> builder
                                .path("/events/{id}")
                                .build(id))
                        .retrieve(), EventDto.class
        );
    }

    // GET api/v1/events
    @Override
    public ResponseEntity<List<EventDto>> getAllEvents() {
        return proxyRestService.proxyRequest(
                () -> restClient.get()
                        .uri(builder -> builder
                                .path("/events")
                                .build())
                        .retrieve(), new ParameterizedTypeReference<List<EventDto>>() {}
        );
    }

    //GET api/v1/events?month=month&day=day
    @Override
    public ResponseEntity<EventDto> getEventByDate(MonthDay monthDay) {
        int month = monthDay.getMonthValue();
        int day = monthDay.getDayOfMonth();

        return proxyRestService.proxyRequest(
                () -> restClient.get()
                        .uri(builder -> builder
                                .path("/events")
                                .queryParam("month", month)
                                .queryParam("day", day)
                                .build())
                        .retrieve(), EventDto.class
        );
    }

    // DELETE /api/v1/events/{id}
    @Override
    public ResponseEntity<Void> deleteEvent(long id) {
        return proxyRestService.proxyRequest(
                () -> restClient.delete()
                        .uri(builder -> builder
                                .path("/events")
                                .build(id))
                        .retrieve(), Void.class
        );
    }
}
