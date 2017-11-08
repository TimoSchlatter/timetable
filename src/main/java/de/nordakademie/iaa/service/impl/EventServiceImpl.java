package de.nordakademie.iaa.service.impl;

import de.nordakademie.iaa.dao.EventDAO;
import de.nordakademie.iaa.model.Event;
import de.nordakademie.iaa.model.Group;
import de.nordakademie.iaa.model.Room;
import de.nordakademie.iaa.model.Subject;
import de.nordakademie.iaa.service.EventService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import de.nordakademie.iaa.service.exception.RoomTooSmallForGroupException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
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
    public void deleteEvent(Long id) throws EntityNotFoundException {
        Event event = loadEvent(id);
        if (event == null) {
            throw new EntityNotFoundException();
        }
        eventDAO.delete(event);
    }

    @Override
    public Event findEventByDateAndStartTimeAndEndTimeAndGroup(LocalDate date, LocalTime startTime, LocalTime endTime, Group group) {
        return eventDAO.findByDateAndStartTimeAndEndTimeAndGroup(date, startTime, endTime, group);
    }

    @Override
    public List<Event> findEventByTime(LocalDate date, LocalTime startTime, LocalTime endTime) {
        return eventDAO.findByTime(date, startTime, endTime);
    }

    @Override
    public void deleteEventByGroup(Group group) {
        eventDAO.deleteByGroup(group);
    }

    @Override
    public void deleteEventBySubject(Subject subject) {
        eventDAO.deleteBySubject(subject);
    }
}
