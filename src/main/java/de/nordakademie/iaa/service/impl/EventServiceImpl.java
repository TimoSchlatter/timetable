package de.nordakademie.iaa.service.impl;

import de.nordakademie.iaa.dao.EventDAO;
import de.nordakademie.iaa.model.Event;
import de.nordakademie.iaa.service.EventService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EventServiceImpl implements EventService {

    private EventDAO eventDAO;

    @Autowired
    public EventServiceImpl(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Override
    public void saveEvent(Event event) {
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
}
