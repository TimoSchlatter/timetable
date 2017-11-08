package service;


import de.nordakademie.iaa.dao.EventDAO;
import de.nordakademie.iaa.model.*;
import de.nordakademie.iaa.service.EventService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import de.nordakademie.iaa.service.exception.RoomTooSmallForGroupException;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class EventServiceTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventDAO eventDAO;

    private final Room room1 = new Room(15, "A", 40, "001", RoomType.LECTUREROOM);
    private final Room room2 = new Room(15, "A", 30, "002", RoomType.COMPUTERROOM);
    private final Set<Room> rooms = new HashSet<>(Arrays.asList(room1, room2));
    private final Set<Docent> docents = new HashSet<>(Arrays.asList(new Docent("a@a.com", "Ed", "Sy", "0115273487", "Dr.", true, 20)));
    private final Group group = new Century("I14a", 30, 30);
    private final LocalDate date = LocalDate.of(2018, Month.APRIL, 1);
    private final LocalTime startTime = LocalTime.of(9, 30);
    private final LocalTime endTime = LocalTime.of(11, 30);
    private final Subject subject = new Subject(30, SubjectType.EXAM, new Course("Test", "I", 154));
    private final Event event = new Event(rooms, docents, group, date, startTime, endTime, subject);

    @Test
    public void testSaveEvent() throws RoomTooSmallForGroupException {
        eventService.saveEvent(event);
        Mockito.verify(eventDAO, times(1)).save(event);
    }

    @Test(expected = RoomTooSmallForGroupException.class)
    public void testSaveEventWithTooSmallRoom() throws RoomTooSmallForGroupException {
        room2.setMaxSeats(group.calculateNumberOfStudents()-1);
        try {
            eventService.saveEvent(event);
        } catch (RoomTooSmallForGroupException e) {
            verify(eventDAO, times(0)).save(event);
            assertEquals("Der " + room2 + " ist zu klein für " + group, e.getMessage());
            throw e;
        }
    }

    @Test(expected = RoomTooSmallForGroupException.class)
    public void testSaveEventWithTooSmallRooms() throws RoomTooSmallForGroupException {
        room1.setMaxSeats(group.calculateNumberOfStudents()-1);
        room2.setMaxSeats(group.calculateNumberOfStudents()-1);
        eventService.saveEvent(event);
        Mockito.verify(eventDAO, times(0)).save(event);
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

    @Test
    public void testFindEventByDateAndStartTimeAndEndTimeAndGroup() {
        eventService.findEventByDateAndStartTimeAndEndTimeAndGroup(date, startTime, endTime, group);
        verify(eventDAO, times(1)).findByDateAndStartTimeAndEndTimeAndGroup(date, startTime, endTime, group);
    }

    @Test
    public void testFindEventByTime() {
        eventService.findEventByTime(date, startTime, endTime);
        verify(eventDAO, times(1)).findByTime(date, startTime, endTime);
    }

    @Test
    public void testDeleteEventByGroup() {
        eventService.deleteEventByGroup(group);
        verify(eventDAO, times(1)).deleteByGroup(group);
    }

    @Test
    public void testDeleteEventBySubject() {
        eventService.deleteEventBySubject(subject);
        verify(eventDAO, times(1)).deleteBySubject(subject);
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