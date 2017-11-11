package service;


import de.nordakademie.iaa.dao.EventDAO;
import de.nordakademie.iaa.model.*;
import de.nordakademie.iaa.service.EventService;

import de.nordakademie.iaa.service.exception.RoomTooSmallForGroupException;
import de.nordakademie.iaa.service.exception.StartTimeAfterEndTimeException;
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
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class EventServiceTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventDAO eventDAO;

    private final Long id = 1L;
    private final Room room1 = new Room(15, "A", 40, "001", RoomType.LECTUREROOM);
    private final Room room2 = new Room(15, "A", 30, "002", RoomType.COMPUTERROOM);
    private final Set<Room> rooms1 = new HashSet<>(Arrays.asList(room1, room2));
    private final Room room3 = new Room(15, "A", 40, "003", RoomType.LECTUREROOM);
    private final Set<Room> rooms2 = new HashSet<>(Arrays.asList(room3));
    private final Docent docent1 = new Docent("a@a.com", "Ed", "Sy", "0115273487", "Dr.", true, 20);
    private final Set<Docent> docents1 = new HashSet<>(Arrays.asList(docent1));
    private final Docent docent2 = new Docent("b@b.com", "Bob", "Sy", "0115541487", "Prof.", false, 30);
    private final Set<Docent> docents2 = new HashSet<>(Arrays.asList(docent2));
    private final Century centuryI14a = new Century("I14a", 30, 30);
    private final Maniple manipleI14 = new Maniple("I14", 30, Arrays.asList(centuryI14a));
    private final Cohort cohort14 = new Cohort("I14a", 30, Arrays.asList(manipleI14));
    private final LocalDate date = LocalDate.of(2018, Month.APRIL, 1);
    private final LocalTime startTime = LocalTime.of(9, 30);
    private final LocalTime endTime = LocalTime.of(11, 30);
    private final Subject subject = new Subject(30, SubjectType.EXAM, new Course("Test", "I", 154));
    private final Event event = new Event(rooms1, docents1, centuryI14a, date, startTime, endTime, subject);

    @Test
    public void testSaveEvent() throws Exception {
        eventService.saveEvent(event);
        Mockito.verify(eventDAO, times(1)).save(event);
    }

    @Test(expected = RoomTooSmallForGroupException.class)
    public void testSaveEventWithTooSmallRoom() throws Exception {
        room2.setMaxSeats(centuryI14a.calculateNumberOfStudents()-1);
        try {
            eventService.saveEvent(event);
        } catch (RoomTooSmallForGroupException e) {
            verify(eventDAO, times(0)).save(event);
            assertEquals(room2 + " can not provide enough seats for " + centuryI14a, e.getMessage());
            throw e;
        }
    }

    @Test(expected = RoomTooSmallForGroupException.class)
    public void testSaveEventWithTooSmallRooms() throws Exception {
        room1.setMaxSeats(centuryI14a.calculateNumberOfStudents()-1);
        room2.setMaxSeats(centuryI14a.calculateNumberOfStudents()-1);
        eventService.saveEvent(event);
        Mockito.verify(eventDAO, times(0)).save(event);
    }

    @Test(expected = StartTimeAfterEndTimeException.class)
    public void testSaveEventWithStartTimeNotBeforeEndTime() throws Exception {
        event.setStartTime(endTime);
        try {
            eventService.saveEvent(event);
        } catch (StartTimeAfterEndTimeException e) {
            verify(eventDAO, times(0)).save(event);
            assertEquals("Provided startTime (" + endTime + ") must be before endTime (" + endTime + ")",
                    e.getMessage());
        }
        event.setEndTime(startTime);
        eventService.saveEvent(event);
    }

    @Test
    public void testListEvents() {
        eventService.listEvents();
        Mockito.verify(eventDAO, times(1)).findAll();
    }

    @Test
    public void testLoadEvent() {
        eventService.loadEvent(id);
        Mockito.verify(eventDAO, times(1)).findOne(id);
    }

    @Test
    public void testDeleteNonExistingEvent() {
        when(eventDAO.findOne(id)).thenReturn(null);
        assertFalse(eventService.deleteEvent(id));
        verify(eventDAO, times(0)).deleteById(anyLong());
    }

    @Test
    public void testDeleteExistingEvent() {
        when(eventDAO.findOne(id)).thenReturn(event);
        assertTrue(eventService.deleteEvent(id));
        verify(eventDAO, times(1)).delete(event);
    }

    @Test
    public void testFindEventByDateAndStartTimeAndEndTimeAndGroup() {
        eventService.findEventByDateAndStartTimeAndEndTimeAndGroup(date, startTime, endTime, centuryI14a);
        verify(eventDAO, times(1)).findByDateAndStartTimeAndEndTimeAndGroup(date, startTime, endTime, centuryI14a);
    }

    @Test
    public void testFindEventByTime() {
        eventService.findEventsByTime(date, startTime, endTime);
        verify(eventDAO, times(1)).findByTime(date, startTime, endTime);
    }

    @Test
    public void testDeleteEventByGroup() {
        eventService.deleteEventsByGroup(centuryI14a);
        verify(eventDAO, times(1)).deleteByGroup(centuryI14a);
    }

    @Test
    public void testDeleteEventBySubject() {
        eventService.deleteEventsBySubject(subject);
        verify(eventDAO, times(1)).deleteBySubject(subject);
    }

    @Test
    public void testFindEventsByRoom() {
        eventService.findEventsByRoom(room1);
        verify(eventDAO, times(1)).findByRooms(room1);
    }

    @Test
    public void testFindEventsByGroup() {
        eventService.findEventsByGroup(centuryI14a);
        verify(eventDAO, times(1)).findByGroup(centuryI14a);
    }

    @Test
    public void testFindEventsByDocent() {
        eventService.findEventsByDocent(docent1);
        verify(eventDAO, times(1)).findByDocents(docent1);
    }

    @Test
    public void testFindGroupCollisions() {
        Event concurrentEvent = new Event(rooms2, docents2, cohort14, date, startTime, endTime, subject);
        when(eventDAO.findByTime(date, startTime, endTime.plusMinutes(event.calculateMinChangeoverTime())))
                .thenReturn(Arrays.asList(concurrentEvent));
        List<String> collisions = eventService.findCollisions(event);
        assertEquals(1, collisions.size());
        assertEquals(event + ": " + event.getGroup() + " ist bereits für folgendes Event eingeplant: "
                + concurrentEvent, collisions.get(0));

        when(eventDAO.findByTime(date, startTime, endTime.plusMinutes(concurrentEvent.calculateMinChangeoverTime())))
                .thenReturn(Arrays.asList(event));
        collisions = eventService.findCollisions(concurrentEvent);
        assertEquals(1, collisions.size());
        assertEquals(concurrentEvent + ": " + concurrentEvent.getGroup()
                + " ist bereits für folgendes Event eingeplant: " + event, collisions.get(0));
    }

    @Test
    public void testFindRoomCollisions() {
        Event concurrentEvent =
                new Event(rooms1, docents2, new Century("I14b", 30, 30), date, startTime, endTime, subject);
        when(eventDAO.findByTime(date, startTime, endTime.plusMinutes(event.calculateMinChangeoverTime())))
                .thenReturn(Arrays.asList(concurrentEvent));
        List<String> collisions = eventService.findCollisions(event);
        assertEquals(rooms1.size(), collisions.size());
        rooms1.forEach(room -> assertTrue(collisions.contains(event + ": " + room
                + " ist bereits für folgendes Event eingeplant: " + concurrentEvent)));
    }

    @Test
    public void testFindDocentCollisions() {
        Event concurrentEvent =
                new Event(rooms2, docents1, new Century("I14b", 30, 30), date, startTime, endTime, subject);
        when(eventDAO.findByTime(date, startTime, endTime.plusMinutes(event.calculateMinChangeoverTime())))
                .thenReturn(Arrays.asList(concurrentEvent));
        List<String> collisions = eventService.findCollisions(event);
        assertEquals(docents1.size(), collisions.size());
        docents1.forEach(docent -> assertTrue(collisions.contains(event + ": " + docent
                + " ist bereits für folgendes Event eingeplant: " + concurrentEvent)));
    }

    @Test
    public void testFindCollisions() {
        Event concurrentEvent = new Event(rooms1, docents1, centuryI14a, date, startTime, endTime, subject);
        when(eventDAO.findByTime(date, startTime, endTime.plusMinutes(event.calculateMinChangeoverTime())))
                .thenReturn(Arrays.asList(concurrentEvent));
        List<String> collisions = eventService.findCollisions(event);
        assertEquals(docents1.size() + rooms1.size() + 1, collisions.size());
        collisions = eventService.findCollisions(event, concurrentEvent);
        assertEquals(0, collisions.size());
    }

    @After
    public void clear() {
        Mockito.reset(eventDAO);
    }

    @Configuration
    static class TestConfiguration {

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