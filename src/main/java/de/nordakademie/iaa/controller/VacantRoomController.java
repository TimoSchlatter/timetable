package de.nordakademie.iaa.controller;

import de.nordakademie.iaa.model.Event;
import de.nordakademie.iaa.model.Group;
import de.nordakademie.iaa.model.Room;
import de.nordakademie.iaa.service.EventService;
import de.nordakademie.iaa.service.GroupService;
import de.nordakademie.iaa.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * REST-Controller for vacant room entities.
 *
 * @author Timo Schlatter
 */
@Transactional
@RestController
@RequestMapping("/vacantRooms")
public class VacantRoomController {

    private EventService eventService;
    private GroupService groupService;
    private RoomService roomService;

    @Autowired
    public VacantRoomController(EventService eventService, GroupService groupService, RoomService roomService) {
        this.eventService = eventService;
        this.groupService = groupService;
        this.roomService = roomService;
    }

    /**
     * List all vacant rooms for a given time frame.
     *
     * @param date      on which the room should be vacant.
     * @param startTime from when the room should be vacant.
     * @param endTime   until the room should be vacant.
     * @return the list of rooms.
     */
    @GetMapping
    public List<Room> listVacantRooms(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                      @RequestParam("startTime") @DateTimeFormat(pattern = "HH:mm:ss") LocalTime startTime,
                                      @RequestParam("endTime") @DateTimeFormat(pattern = "HH:mm:ss") LocalTime endTime,
                                      @RequestParam("groupId") Long groupId,
                                      @RequestParam(value = "eventId", required = false) Long eventId) {
        Group group = groupService.loadGroup(groupId);
        if (group != null) {
            List<Room> rooms = new ArrayList<>(roomService.listRooms());
            if (!rooms.isEmpty()) {
                List<Event> events = new ArrayList<>(eventService.findEventsByTime(date, startTime, endTime));
                if (!events.isEmpty()) {
                    if (eventId != null) {
                        Event event = eventService.loadEvent(eventId);
                        if (event != null) {
                            events.remove(event);
                        }
                    }
                    events.forEach(event -> event.getRooms().forEach(rooms::remove));
                }
                rooms.removeIf(room -> room.getMaxSeats() < group.calculateNumberOfStudents());
            }
            return rooms;
        }
        return new ArrayList<>();
    }
}