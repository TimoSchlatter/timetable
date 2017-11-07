package de.nordakademie.iaa.controller;


import de.nordakademie.iaa.model.Event;
import de.nordakademie.iaa.model.Room;
import de.nordakademie.iaa.service.EventService;
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

@Transactional
@RestController
@RequestMapping("/vacantRooms")
public class VacantRoomController {

    private EventService eventService;
    private RoomService roomService;

    @Autowired
    public VacantRoomController(EventService eventService, RoomService roomService) {
        this.eventService = eventService;
        this.roomService = roomService;
    }

    /**
     * List all vacant rooms for a given time.
     *
     * @param date on which the room should be vacant.
     * @param startTime from when the room should be vacant.
     * @param endTime until the room should be vacant.
     * @return the list of rooms.
     */
    @GetMapping
    public List<Room> listVacantRooms(@RequestParam("date")@DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate date,
                                @RequestParam("startTime")@DateTimeFormat(pattern = "HH:mm")LocalTime startTime,
                                @RequestParam("endTime")@DateTimeFormat(pattern = "HH:mm")LocalTime endTime,
                                @RequestParam(value = "groupSize", required = false)Integer groupSize) {
        List<Event> events = eventService.findEventByTime(date, startTime, endTime);
        List<Room> rooms = new ArrayList<>(roomService.listRooms());
        if (!rooms.isEmpty()) {
            if (!events.isEmpty()) {
                events.forEach(event -> event.getRooms().forEach(rooms::remove));
            }
            if (groupSize != null) {
                rooms.removeIf(room -> room.getMaxSeats() < groupSize);
            }
        }
        return rooms;
    }
}