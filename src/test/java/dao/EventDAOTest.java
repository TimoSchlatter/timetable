package dao;

import de.nordakademie.iaa.Application;
import de.nordakademie.iaa.dao.*;
import de.nordakademie.iaa.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Test class for EventDAO class.
 *
 * @author Arvid Ottenberg
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@Transactional
public class EventDAOTest {

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private DocentDAO docentDAO;

    @Autowired
    private RoomDAO roomDAO;

    @Autowired
    private SubjectDAO subjectDAO;

    @Autowired
    private CourseDAO courseDAO;

    @PersistenceContext
    private EntityManager entityManager;

    private Event event;
    private Group group;
    private Docent docent;
    private Subject subject;
    private Room room;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;

    @Before
    public void setupData() {
        Course course = new Course("Wuerfel Tricks", "I", 123);
        courseDAO.save(course);

        subject = new Subject(20, SubjectType.EXAM, course);
        subjectDAO.save(subject);

        group = new Century("I14a", 20, 30);
        groupDAO.save(group);

        date = LocalDate.of(2017, 12, 24);
        startTime = LocalTime.of(20, 0);
        endTime = LocalTime.of(22, 30);

        docent = new Docent("test@docent.com", "John", "Doe", "0123123123", "Dr.Dr.", true, 20);
        docentDAO.save(docent);
        HashSet<Docent> docents = new HashSet<>();
        docents.add(docent);

        room = new Room(20, "X", 42, "999", RoomType.COMPUTERROOM);
        roomDAO.save(room);
        HashSet<Room> rooms = new HashSet<>();
        rooms.add(room);

        event = new Event(rooms, docents, group, date, startTime, endTime, subject);
        eventDAO.save(event);
    }

    @Test
    public void testFindOne() {
        Event event = eventDAO.findOne(this.event.getId());
        compareEvent(event);
    }

    @Test
    public void testFindAll() {
        List<Event> events = eventDAO.findAll();
        assertEquals(1, events.size());
        events.forEach(this::compareEvent);
    }

    @Test
    public void testFindByTime() {
        List<Event> events = eventDAO.findByTime(date, endTime.plusMinutes(2), endTime.plusMinutes(10));
        assertTrue(events.isEmpty());
        events = eventDAO.findByTime(date, startTime.minusMinutes(10), endTime.minusMinutes(10));
        assertEquals(1, events.size());
        events.forEach(this::compareEvent);
    }

    @Test
    public void testFindByDateAndStartTimeAndEndTimeAndGroup() {
        Event event = eventDAO.findByDateAndStartTimeAndEndTimeAndGroup(this.event.getDate(), this.event.getStartTime(), this.event.getEndTime(), this.event.getGroup());
        compareEvent(event);
    }

    @Test
    public void testFindByRooms() {
        List<Event> events = eventDAO.findByRooms(room);
        assertEquals(1, events.size());
        events.forEach(this::compareEvent);
    }

    @Test
    public void testFindByGroup() {
        List<Event> events = eventDAO.findByGroup(group);
        assertEquals(1, events.size());
        events.forEach(this::compareEvent);
    }

    @Test
    public void testFindByDocents() {
        List<Event> events = eventDAO.findByDocents(docent);
        assertEquals(1, events.size());
        events.forEach(this::compareEvent);
    }

    @Test
    public void testDelete() {
        eventDAO.delete(event);
        assertTrue(eventDAO.findAll().isEmpty());

    }

    @Test
    public void testDeleteByGroup() {
        assertEquals(1, eventDAO.findAll().size());
        eventDAO.deleteByGroup(group);
        assertTrue(eventDAO.findAll().isEmpty());
    }

    @Test
    public void testDeleteBySubject() {
        assertEquals(1, eventDAO.findAll().size());
        eventDAO.deleteBySubject(subject);
        assertTrue(eventDAO.findAll().isEmpty());
    }

    @After
    public void tearDown() {
        entityManager.clear();
    }

    private void compareEvent(Event event) {
        assertEquals(this.event.getDate(), event.getDate());
        assertEquals(this.event.getDocents(), event.getDocents());
        assertEquals(this.event.getEndTime(), event.getEndTime());
        assertEquals(this.event.getStartTime(), event.getStartTime());
        assertEquals(this.event.getGroup(), event.getGroup());
        assertEquals(this.event.getSubject(), event.getSubject());
        assertEquals(this.event.getRooms(), event.getRooms());
    }
}