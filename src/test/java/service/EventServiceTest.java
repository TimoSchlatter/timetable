package service;


import de.nordakademie.iaa.dao.EventDAO;
import de.nordakademie.iaa.model.Event;
import de.nordakademie.iaa.service.EventService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import de.nordakademie.iaa.service.impl.EventServiceImpl;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Matchers.anyLong;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class EventServiceTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventDAO eventDAO;

    private final Event event = new Event();

    @Test
    public void testSaveEvent() {
        eventService.saveEvent(event);
        Mockito.verify(eventDAO, times(1)).save(event);
    }

    @Test
    public void testListEvents() {
        eventService.listEvents();
        Mockito.verify(eventDAO, times(1)).findAll();
    }

    @Test
    public void testLoadEvent() {
        final Long id = 123L;
        eventService.loadEvent(id);
        Mockito.verify(eventDAO, times(1)).findOne(id);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteNonExistingEvent() throws EntityNotFoundException {
        final Long id = 123L;
        Mockito.when(eventDAO.findOne(anyLong())).thenReturn(null);
        eventService.deleteEvent(id);
    }

    @Test
    public void testDeleteExistingEvent() throws EntityNotFoundException {
        final Long id = 123L;
        Mockito.when(eventDAO.findOne(anyLong())).thenReturn(event);
        eventService.deleteEvent(id);
        Mockito.verify(eventDAO, times(1)).findOne(id);
        Mockito.verify(eventDAO, times(1)).delete(event);
    }

    @After
    public void clear() {
        Mockito.reset(eventDAO);
    }

    @Configuration
    static class AccountServiceTestContextConfiguration {

        @Bean
        public EventService eventService() {
            return new EventServiceImpl(eventDAO());
        }

        @Bean
        public EventDAO eventDAO() {
            return Mockito.mock(EventDAO.class);
        }
    }
}