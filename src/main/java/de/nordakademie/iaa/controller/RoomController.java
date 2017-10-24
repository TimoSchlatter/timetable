package de.nordakademie.iaa.controller;


import de.nordakademie.iaa.model.Room;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import de.nordakademie.iaa.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
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
     * Saves the given room (either by creating a new one or updating an existing).
     *
     * @param room The room to save.
     */
    @PutMapping
    public void saveRoom(@RequestBody Room room) {
        roomService.saveRoom(room);
    }

    /**
     * Deletes the room with given id.
     *
     * @param id The id of the room to be deleted.
     */
    @DeleteMapping
    @RequestMapping("/{id}")
    public ResponseEntity deleteRoom(@PathVariable Long id) {
        try {
            roomService.deleteRoom(id);
            return ResponseEntity.ok(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
