package de.nordakademie.iaa.controller;


import de.nordakademie.iaa.model.Event;
import de.nordakademie.iaa.model.Room;
import de.nordakademie.iaa.service.EventService;
import de.nordakademie.iaa.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Transactional
@RestController
@RequestMapping("/rooms")
public class RoomController {

    private EventService eventService;
    private RoomService roomService;

    @Autowired
    public RoomController(EventService eventService, RoomService roomService) {
        this.eventService = eventService;
        this.roomService = roomService;
    }

    /**
     * List all rooms.
     *
     * @return the list of rooms.
     */
    @GetMapping
    public List<Room> listRooms() {
        return roomService.listRooms();
    }

    /**
     * Saves the given room.
     *
     * @param room The room to save.
     */
    @PostMapping
    public ResponseEntity saveRoom(@RequestBody Room room) {
        try {
            if (roomService.findByBuildingAndNumber(room.getBuilding(), room.getNumber()) == null) {
                roomService.saveRoom(room);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        } catch (Exception ignored) {
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Updates the given room.
     *
     * @param room The room to update.
     */
    @RequestMapping(value = "/{id}", method = PUT)
    public ResponseEntity updateRoom(@PathVariable Long id, @RequestBody Room room) {
        try {
            if (roomService.loadRoom(id) != null) {
                roomService.saveRoom(room);
                return ResponseEntity.ok().build();
            }
        } catch (Exception ignored) {
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Deletes the room with given id.
     *
     * @param id The id of the room to be deleted.
     */
    @RequestMapping(value = "/{id}", method = DELETE)
    public ResponseEntity deleteRoom(@PathVariable Long id) {
        Room room = roomService.loadRoom(id);
        if (room != null) {
            List<Event> eventsWithRoom = eventService.findEventsByRoom(room);
            eventsWithRoom.forEach(event -> {
                if (event.getRooms().size() == 1) {
                    eventService.deleteEvent(event.getId());
                } else {
                    event.removeRoom(room);
                }
            });
            roomService.deleteRoom(id);
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.badRequest().build();
    }

}
