package de.nordakademie.iaa.controller;


import de.nordakademie.iaa.model.Event;
import de.nordakademie.iaa.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Transactional
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
     * Saves the given event.
     *
     * @param event The event to save.
     */
    @PostMapping
    public ResponseEntity saveEvent(@RequestBody Event event, @RequestParam(value = "repeatWeeks", required = false) Integer repeatWeeks) {

        int repeat = (repeatWeeks == null ? 1 : repeatWeeks);
        int created = 0;
        LocalDate date = event.getDate();
        for (int i = 0; i < repeat; i++) {
            try {
                if (eventService.findEventByDateAndStartTimeAndEndTimeAndGroup(date, event.getStartTime(),
                        event.getEndTime(), event.getGroup()) == null) {
                    event.setDate(date);
                    eventService.saveEvent(event);
                    created++;
                }
            } catch (Exception ignored) {}
            date = date.plusDays(7);
        }
        if (created > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Updates the given event.
     *
     * @param event The event to update.
     */
    @RequestMapping(value = "/{id}", method = PUT)
    public ResponseEntity updateEvent(@PathVariable Long id, @RequestBody Event event) {
        try {
            if (eventService.loadEvent(id) != null) {
                eventService.saveEvent(event);
                return ResponseEntity.ok().build();
            }
        } catch (Exception ignored) {
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Deletes the event with given id.
     *
     * @param id The id of the event to be deleted.
     */
    @RequestMapping(value = "/{id}", method = DELETE)
    public ResponseEntity deleteEvent(@PathVariable Long id) {
        return (eventService.deleteEvent(id) ? ResponseEntity.ok(null) : ResponseEntity.badRequest().build());
    }
}
