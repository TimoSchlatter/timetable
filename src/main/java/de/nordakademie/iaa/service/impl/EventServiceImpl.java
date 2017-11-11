package de.nordakademie.iaa.service.impl;

import de.nordakademie.iaa.dao.EventDAO;
import de.nordakademie.iaa.model.*;
import de.nordakademie.iaa.service.EventService;
import de.nordakademie.iaa.service.exception.RoomTooSmallForGroupException;
import de.nordakademie.iaa.service.exception.StartTimeAfterEndTimeException;
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

    private final EventDAO eventDAO;

    @Autowired
    public EventServiceImpl(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Override
    public void saveEvent(Event event) throws RoomTooSmallForGroupException, StartTimeAfterEndTimeException {
        LocalTime startTime = event.getStartTime();
        LocalTime endTime = event.getEndTime();
        if (!startTime.isBefore(endTime)) {
            throw new StartTimeAfterEndTimeException(startTime, endTime);
        }
        Group eventGroup = event.getGroup();
        int students = eventGroup.calculateNumberOfStudents();
        Optional<Room> optionalRoom = event.getRooms().stream()
                .filter(room -> room.getMaxSeats() < students).findFirst();
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
    public Event findEventByDateAndStartTimeAndEndTimeAndGroup(LocalDate date, LocalTime startTime, LocalTime endTime,
                                                               Group group) {
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
    public List<String> findCollisions(Event newEvent, Event oldEvent) {
        List<String> collisions = new ArrayList<>();
        List<Event> concurrentEvents = new ArrayList<>(findEventsByTime(newEvent.getDate(), newEvent.getStartTime(),
                (newEvent.getEndTime().plusMinutes(newEvent.calculateMinChangeoverTime()))));
        if (oldEvent != null) {
            concurrentEvents.remove(oldEvent);
        }
        for (Event concurrentEvent : concurrentEvents) {
            // docents
            newEvent.getDocents().stream()
                    .filter(docent -> concurrentEvent.getDocents().contains(docent))
                    .forEach(docent -> collisions.add(createCollisionString(newEvent, docent, concurrentEvent)));
            // rooms
            newEvent.getRooms().stream()
                    .filter(room -> concurrentEvent.getRooms().contains(room))
                    .forEach(room -> collisions.add(createCollisionString(newEvent, room, concurrentEvent)));
            // group
            Group newGroup = newEvent.getGroup();
            Group concurrentGroup = concurrentEvent.getGroup();
            if (newGroup.equals(concurrentGroup) || concurrentGroup.hasSubGroup(newGroup) ||
                    newGroup.hasSubGroup(concurrentGroup)) {
                collisions.add(createCollisionString(newEvent, newGroup, concurrentEvent));
            }
        }
        return collisions;
    }

    @Override
    public List<String> findCollisions(Event newEvent) {
        return findCollisions(newEvent, null);
    }

    @Override
    public List<Event> findEventsByGroup(Group group) { return eventDAO.findByGroup(group); }

    private String createCollisionString(Event eventToBeCreated, Object collidingObject, Event concurrentEvent) {
        return eventToBeCreated + ": " + collidingObject + " ist bereits für folgendes Event eingeplant: "
                + concurrentEvent;
    }
}
