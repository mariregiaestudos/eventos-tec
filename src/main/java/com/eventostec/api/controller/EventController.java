package com.eventostec.api.controller;

import com.eventostec.api.Domain.event.Event;
import com.eventostec.api.Domain.event.EventRequestDTO;
import com.eventostec.api.Services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/event")
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Event> create(@ModelAttribute EventRequestDTO event) throws IOException {
        Event newEvent = this.eventService.createEvent(event);
        return ResponseEntity.ok(newEvent);
    }
}
