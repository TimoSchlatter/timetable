package de.nordakademie.iaa.controller;


import de.nordakademie.iaa.model.Event;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import de.nordakademie.iaa.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * List all events.
     *
     * @return the list of events.
     */
    @GetMapping
    public List<Event> listEvents() {
        return eventService.listEvents();
    }

    /**
     * Saves the given event (either by creating a new one or updating an existing).
     *
     * @param event The event to save.
     */
    @PutMapping
    public void saveEvent(@RequestBody Event event) {
        eventService.saveEvent(event);
    }

    /**
     * Deletes the event with given id.
     *
     * @param id The id of the event to be deleted.
     */
    @DeleteMapping
    @RequestMapping("/{id}")
    public ResponseEntity deleteEvent(@PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.ok(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
