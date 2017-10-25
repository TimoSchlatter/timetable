package dao;

import de.nordakademie.iaa.ApplicationConfig;
import de.nordakademie.iaa.dao.*;
import de.nordakademie.iaa.model.Event;
import org.junit.runner.RunWith;
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
    private GroupDAO groupDAO;
    private DocentDAO docentDAO;
    private LectureDAO lectureDAO;
    private RoomDAO roomDAO;
    private CourseDAO courseDAO;

    @PersistenceContext
    private EntityManager entityManager;

    private Event event;
/**
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
    public void setLectureDAO(LectureDAO lectureDAO) {
        this.lectureDAO = lectureDAO;
    }

    @Autowired
    public void setRoomDAO(RoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }

    @Autowired
    public void setCourseDAO(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    @Before
    public void setupData() {
        HashSet<Course> courses = new HashSet<>();
        Course course = new Course('I',123,"Test Driven Development");
        courseDAO.save(course);
        courses.add(course);

        Docent docent = new Docent("test@docent.com", "John", "Doe", "0123123123", "Dr.Dr.", true, 20, courses);
        docentDAO.save(docent);
        HashSet<Docent> docents = new HashSet<>();
        docents.add(docent);

        Group group = new Century("I14a",30,20);
        groupDAO.save(group);

        LocalDate date = LocalDate.of(2017,12,24);
        LocalTime startTime = LocalTime.of(20,0);
        LocalTime endTime = LocalTime.of(22,30);

        HashSet<Room> rooms = new HashSet<>();
        Room room = new Room(30,"X",45,"999",RoomType.LABORATORY);
        roomDAO.save(room);
        rooms.add(room);

        Lecture lecture = new Lecture(5,"Kurze Methoden Teil I",course,false);
        lectureDAO.save(lecture);
        event = new Event(docents,group,date,startTime,endTime,rooms,lecture);
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
        assertEquals(this.event.getDate(), event.getDate());
        assertEquals(this.event.getDocents(), event.getDocents());
        assertEquals(this.event.getEndTime(), event.getEndTime());
        assertEquals(this.event.getStartTime(), event.getStartTime());
        assertEquals(this.event.getGroup(), event.getGroup());
        assertEquals(this.event.getSubject(), event.getSubject());
        assertEquals(this.event.getRooms(), event.getRooms());
    }
**/
}

