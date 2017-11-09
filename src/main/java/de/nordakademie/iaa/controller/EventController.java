package de.nordakademie.iaa.controller;

import de.nordakademie.iaa.model.Docent;
import de.nordakademie.iaa.model.Event;
import de.nordakademie.iaa.model.Group;
import de.nordakademie.iaa.model.Room;
import de.nordakademie.iaa.service.DocentService;
import de.nordakademie.iaa.service.EventService;
import de.nordakademie.iaa.service.GroupService;
import de.nordakademie.iaa.service.RoomService;
import de.nordakademie.iaa.service.exception.RoomTooSmallForGroupException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * REST-Controller for event entities.
 *
 * @author Timo Schlatter
 */
@Transactional
@RestController
@RequestMapping("/events")
public class EventController {

    private DocentService docentService;
    private EventService eventService;
    private GroupService groupService;
    private RoomService roomService;

    @Autowired
    public EventController(DocentService docentService, EventService eventService, GroupService groupService, RoomService roomService) {
        this.docentService = docentService;
        this.eventService = eventService;
        this.groupService = groupService;
        this.roomService = roomService;
    }

    /**
     * Lists all events.
     *
     * @return the list of all saved events.
     */
    @GetMapping
    public List<Event> listEvents() {
        return eventService.listEvents();
    }

    /**
     * Saves the given event.
     *
     * @param event the event to save.
     * @return status OK or BAD_REQUEST (if the given event is already existing or saving failed).
     */
    @PostMapping
    public ResponseEntity saveEvent(@RequestBody Event event,
                                    @RequestParam(value = "repeatWeeks", required = false) Integer repeatWeeks,
                                    @RequestParam(value = "ignoreCollisions", required = false) Boolean ignoreCollisions) {
        repeatWeeks = (repeatWeeks == null ? 1 : repeatWeeks);
        ignoreCollisions = (ignoreCollisions == null ? false : ignoreCollisions);
        LocalDate startDate = event.getDate();
        List<String> collisions = new ArrayList<>();
        List<Event> eventsToSave = new ArrayList<>();
        for (int i = 0; i < repeatWeeks; i++) {
            event.setDate(startDate.plusDays(i * 7));
            eventsToSave.add(event);
        }
        eventsToSave.forEach(eventToSave -> collisions.addAll(eventService.findCollisions(eventToSave)));
        if (collisions.isEmpty() || ignoreCollisions) {
            int created = 0;
            for (Event eventToSave : eventsToSave) {
                created = (saveEvent(eventToSave) ? created + 1 : created);
            }
            if (created > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).body(created + " von " + repeatWeeks +
                        " Events wurden erzeugt");
            }
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(collisions);
        }
    }

    /**
     * Updates the event with given id.
     *
     * @param id    identifier for event to update.
     * @param event new values for event.
     * @return status OK or BAD_REQUEST (if update failed).
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
     * @param id identifier for event to delete.
     * @return status OK or BAD_REQUEST (if deletion failed or no event was found for given id).
     */
    @RequestMapping(value = "/{id}", method = DELETE)
    public ResponseEntity deleteEvent(@PathVariable Long id) {
        return (eventService.deleteEvent(id) ? ResponseEntity.ok(null) : ResponseEntity.badRequest().build());
    }

    /**
     * Lists all events which contain a given docent.
     *
     * @param docentId the identifier for the docent.
     * @return the list of filtered events.
     */
    @RequestMapping(value = "/findByDocent", method = GET)
    public List<Event> findEventsByDocentId(@RequestParam("id") Long docentId) {
        Docent docent = docentService.loadDocent(docentId);
        if (docent != null) {
            return eventService.findEventsByDocent(docent);
        }
        return new ArrayList<>();
    }

    /**
     * Lists all events which contain a given group.
     *
     * @param groupId the identifier for the group.
     * @return the list of filtered events.
     */
    @RequestMapping(value = "/findByGroup", method = GET)
    public List<Event> findEventsByGroupId(@RequestParam("id") Long groupId) {
        Group group = groupService.loadGroup(groupId);
        if (group != null) {
            return eventService.findEventsByGroup(group);
        }
        return new ArrayList<>();
    }

    /**
     * Lists all events which contain a given room.
     *
     * @param roomId the identifier for the room.
     * @return the list of filtered events.
     */
    @RequestMapping(value = "/findByRoom", method = GET)
    public List<Event> findEventsByRoomId(@RequestParam("id") Long roomId) {
        Room room = roomService.loadRoom(roomId);
        if (room != null) {
            return eventService.findEventsByRoom(room);
        }
        return new ArrayList<>();
    }

    private boolean saveEvent(Event event) {
        try {
            if (eventService.findEventByDateAndStartTimeAndEndTimeAndGroup(event.getDate(),
                    event.getStartTime(), event.getEndTime(), event.getGroup()) == null) {
                eventService.saveEvent(event);
                return true;
            }
        } catch (RoomTooSmallForGroupException ignored) {
        }
        return false;
    }
}
