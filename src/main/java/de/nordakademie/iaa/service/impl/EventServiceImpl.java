package de.nordakademie.iaa.service.impl;

import de.nordakademie.iaa.dao.EventDAO;
import de.nordakademie.iaa.model.*;
import de.nordakademie.iaa.service.EventService;
import de.nordakademie.iaa.service.exception.RoomTooSmallForGroupException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EventServiceImpl implements EventService {

    private EventDAO eventDAO;

    @Autowired
    public EventServiceImpl(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Override
    public void saveEvent(Event event) throws RoomTooSmallForGroupException {
        Group eventGroup = event.getGroup();
        int students = eventGroup.calculateNumberOfStudents();
        Optional<Room> optionalRoom = event.getRooms().stream().filter(room -> room.getMaxSeats() < students).findFirst();
        if (optionalRoom.isPresent()) {
            throw new RoomTooSmallForGroupException(optionalRoom.get(), eventGroup);
        }
        eventDAO.save(event);
    }

    @Override
    public List<Event> listEvents() {
        return eventDAO.findAll();
    }

    @Override
    public Event loadEvent(Long id) {
        return eventDAO.findOne(id);
    }

    @Override
    public boolean deleteEvent(Long id) {
        Event event = loadEvent(id);
        if (event == null) {
            return false;
        }
        eventDAO.delete(event);
        return true;
    }

    @Override
    public Event findEventByDateAndStartTimeAndEndTimeAndGroup(LocalDate date, LocalTime startTime, LocalTime endTime, Group group) {
        return eventDAO.findByDateAndStartTimeAndEndTimeAndGroup(date, startTime, endTime, group);
    }

    @Override
    public List<Event> findEventsByTime(LocalDate date, LocalTime startTime, LocalTime endTime) {
        return eventDAO.findByTime(date, startTime, endTime);
    }

    @Override
    public void deleteEventsByGroup(Group group) {
        eventDAO.deleteByGroup(group);
    }

    @Override
    public void deleteEventsBySubject(Subject subject) {
        eventDAO.deleteBySubject(subject);
    }

    @Override
    public List<Event> findEventsByRoom(Room room) {
        return eventDAO.findByRooms(room);
    }

    @Override
    public List<Event> findEventsByDocent(Docent docent) {
        return eventDAO.findByDocents(docent);
    }

    @Override
    public List<String> findCollisions(Event event) {
        List<String> collisions = new ArrayList<>();
        List<Event> concurrentEvents = findEventsByTime(event.getDate(), event.getStartTime(),
                (event.getEndTime().plusMinutes(event.calculateMinChangeoverTime())));
        for (Event concurrentEvent : concurrentEvents) {
            // docents
            event.getDocents().forEach(docent -> {
                if (concurrentEvent.getDocents().contains(docent)) {
                    collisions.add(createCollisionString(event, docent, concurrentEvent));
                }
            });
            // rooms
            event.getRooms().forEach(room -> {
                if (concurrentEvent.getRooms().contains(room)) {
                    collisions.add(createCollisionString(event, room, concurrentEvent));
                }
            });
            // group
            Group group = event.getGroup();
            if (group.equals(concurrentEvent.getGroup())) {
                collisions.add(createCollisionString(event, group, concurrentEvent));
            }
        }
        return collisions;
    }

    private String createCollisionString(Event eventToBeCreated, Object collidingObject, Event concurrentEvent) {
        return eventToBeCreated.toString() + ": " + collidingObject.toString() +
                " ist bereits für folgendes Event eingeplant: " + concurrentEvent.toString() + "\\\n";
    }

    @Override
    public List<Event> findEventsByGroup(Group group) { return eventDAO.findByGroup(group); }
}
