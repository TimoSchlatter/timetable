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
 * Created by arvid on 25.10.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Application.class})
@Transactional
public class EventDAOTest {

    private EventDAO eventDAO;
    private GroupDAO groupDAO;
    private DocentDAO docentDAO;
    private RoomDAO roomDAO;
    private SubjectDAO subjectDAO;
    private CourseDAO courseDAO;

    @PersistenceContext
    private EntityManager entityManager;

    private Event event;

    @Autowired
    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    @Autowired
    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    @Autowired
    public void setDocentDAO(DocentDAO docentDAO) {
        this.docentDAO = docentDAO;
    }

    @Autowired
    public void setRoomDAO(RoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }

    @Autowired
    public void setSubjectDAO(SubjectDAO subjectDAO) {
        this.subjectDAO = subjectDAO;
    }

    @Autowired
    public void setCourseDAO(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }
    @Before
    public void setupData() {
        Course course = new Course("Wuerfel Tricks","I", 123);
        courseDAO.save(course);

        Subject subject = new Subject(20, SubjectType.EXAM, course);
        subjectDAO.save(subject);

        Group group = new Century("I14a",20,30);
        groupDAO.save(group);

        LocalDate date = LocalDate.of(2017,12,24);
        LocalTime startTime = LocalTime.of(20,0);
        LocalTime endTime = LocalTime.of(22,30);

        Docent docent = new Docent("test@docent.com", "John", "Doe", "0123123123", "Dr.Dr.", true, 20);
        docentDAO.save(docent);
        HashSet<Docent> docents = new HashSet<>();
        docents.add(docent);

        Room room = new Room(20,"X", 42, "999", RoomType.COMPUTERROOM);
        roomDAO.save(room);
        HashSet<Room> rooms = new HashSet<>();
        rooms.add(room);

        event = new Event(rooms, docents, group, date, startTime, endTime, subject);
        eventDAO.save(event);
    }

    @After
    public void tearDown() {
        entityManager.clear();
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
        for(Event event : events) {
            compareEvent(event);
        }
    }

    @Test
    public void testDelete() {
        eventDAO.delete(this.event);
        List<Event> events = eventDAO.findAll();
        assertTrue(events.isEmpty());

    }

    @Test
    public void testDeleteById() {
        eventDAO.deleteById(this.event.getId());
        List<Event> events = eventDAO.findAll();
        assertTrue(events.isEmpty());
    }

    @Test
    public void testFindByDateAndStartTimeAndEndTimeAndGroup() {
        Event event = eventDAO.findByDateAndStartTimeAndEndTimeAndGroup(this.event.getDate(),this.event.getStartTime(), this.event.getEndTime(), this.event.getGroup());
        compareEvent(event);
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

