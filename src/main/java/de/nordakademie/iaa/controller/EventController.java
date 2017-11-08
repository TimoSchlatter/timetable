package de.nordakademie.iaa.controller;


import de.nordakademie.iaa.model.Docent;
import de.nordakademie.iaa.model.Event;
import de.nordakademie.iaa.model.Room;
import de.nordakademie.iaa.service.DocentService;
import de.nordakademie.iaa.service.EventService;
import de.nordakademie.iaa.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Transactional
@RestController
@RequestMapping("/events")
public class EventController {

    private EventService eventService;
    private DocentService docentService;
    private RoomService roomService;

    @Autowired
    public EventController(EventService eventService, DocentService docentService, RoomService roomService) {
        this.eventService = eventService;
        this.docentService = docentService;
        this.roomService = roomService;
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
    public ResponseEntity saveEvent(@RequestBody Event event,
                                    @RequestParam(value = "repeatWeeks", required = false) Integer repeatWeeks,
                                    @RequestParam(value = "ignoreCollision", required = false) Boolean ignoreCollision) {
        repeatWeeks = (repeatWeeks == null ? 1 : repeatWeeks);
        ignoreCollision = (ignoreCollision == null ? false : ignoreCollision);
        LocalDate startDate = event.getDate();
        List<String> collisions = new ArrayList<>();
        List<Event> eventsToSave = new ArrayList<>();
        for (int i = 0; i < repeatWeeks; i++) {
            event.setDate(startDate.plusDays(i * 7));
            eventsToSave.add(event);
        }

        eventsToSave.forEach(eventToSave -> collisions.addAll(eventService.findCollisions(eventToSave)));

        if (collisions.isEmpty() || ignoreCollision) {
            int created = 0;
            for (Event eventToSave : eventsToSave) {
                created = (saveEvent(eventToSave) ? created + 1 : created);
            }
            if (created > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(collisions);
        }
    }

    private boolean saveEvent(Event event) {
        try {
            if (eventService.findEventByDateAndStartTimeAndEndTimeAndGroup(event.getDate(),
                    event.getStartTime(), event.getEndTime(), event.getGroup()) == null) {
                eventService.saveEvent(event);
                return true;
            }
        } catch (Exception ignored) {
        }
        return false;
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

    @RequestMapping(value = "/findByDocent", method = GET)
    public List<Event> findEventsByDocentId(@RequestParam("id")Long docentId) {
        Docent docent = docentService.loadDocent(docentId);
        if (docent != null) {
            return eventService.findEventsByDocent(docent);
        }
        return new ArrayList<>();
    }

    @RequestMapping(value = "/findByRoom", method = GET)
    public List<Event> findEventsByRoomId(@RequestParam("id")Long roomId) {
        Room room = roomService.loadRoom(roomId);
        if (room != null) {
            return eventService.findEventsByRoom(room);
        }
        return new ArrayList<>();
    }
}
