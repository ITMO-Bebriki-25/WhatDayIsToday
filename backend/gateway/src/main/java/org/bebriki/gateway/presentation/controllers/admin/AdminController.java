package org.bebriki.gateway.presentation.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.bebriki.gateway.application.events.services.EventsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {
    private final EventsService eventsService;

    @GetMapping("/avents")
    public ResponseEntity<?> getAllEvents() {
       return eventsService.getAllEvents();
    }
}
