package de.nordakademie.iaa.test.dao;

import de.nordakademie.iaa.ApplicationConfig;
import de.nordakademie.iaa.dao.EventDAO;
import de.nordakademie.iaa.model.Event;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by arvid on 25.10.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfig.class})
@Transactional
public class EventDAOTest {

    private EventDAO eventDAO;

    @PersistenceContext
    private EntityManager entityManager;

    private Event event;

    @Autowired
    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }
/**
    @Before
    public void setupData() {
        HashSet<Course> courses = new HashSet<>();
        Course course = new Course('I',123,"Test Driven Development");
        courses.add(course);

        Docent docent = new Docent("test@docent.com", "John", "Doe", "0123123123", "Dr.Dr.", true, Duration.ofMinutes(20), courses);
        HashSet<Docent> docents = new HashSet<>();
        docents.add(docent);

        Group group;
        LocalDate date;
        LocalTime startTime;
        LocalTime endTime;
        HashSet<Room> rooms;
        Subject subject;
        event = new Event("I14a",42, Duration.ofMinutes(20));
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

    private void compareEvent(Event event) {
        assertEquals(this.event.getNumberOfStudents(), event.getNumberOfStudents());
        assertEquals(this.event.getName(), event.getName());
        assertEquals(this.event.getMinChangeoverTime(), event.getMinChangeoverTime());
    }
    **/
}

