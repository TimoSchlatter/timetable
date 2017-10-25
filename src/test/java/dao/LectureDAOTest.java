package dao;

import de.nordakademie.iaa.ApplicationConfig;
import de.nordakademie.iaa.dao.CourseDAO;
import de.nordakademie.iaa.dao.LectureDAO;
import de.nordakademie.iaa.model.Course;
import de.nordakademie.iaa.model.Lecture;
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
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by arvid on 25.10.17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfig.class})
@Transactional
public class LectureDAOTest {


    private LectureDAO lectureDAO;
    private CourseDAO courseDAO;

    @PersistenceContext
    private EntityManager entityManager;

    private Lecture lecture;

    @Autowired
    public void setLectureDAO(LectureDAO lectureDAO) {
        this.lectureDAO = lectureDAO;
    }

    @Autowired
    public void setCourseDAO(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    @Before
    public void setupData() {
        Course course = new Course('I', 123, "Test Driven Development");
        courseDAO.save(course);
        lecture = new Lecture(20, "Test Vorlesung", course, false);
        lectureDAO.save(lecture);
    }

    @After
    public void tearDown() {
        entityManager.clear();
    }

    @Test
    public void testFindOne() {
        Lecture lecture = lectureDAO.findOne(this.lecture.getId());
        compareLectures(lecture);
    }

    @Test
    public void testFindAll() {
        List<Lecture> lectures = lectureDAO.findAll();
        assertEquals(1, lectures.size());
        for (Lecture lecture : lectures) {
            compareLectures(lecture);
        }
    }

    @Test
    public void testDelete() {
        lectureDAO.delete(this.lecture);
        List<Lecture> lectures = lectureDAO.findAll();
        assertTrue(lectures.isEmpty());

    }

    @Test
    public void testDeleteById() {
        lectureDAO.deleteById(this.lecture.getId());
        List<Lecture> lectures = lectureDAO.findAll();
        assertTrue(lectures.isEmpty());
    }

    private void compareLectures(Lecture lecture) {
        assertEquals(this.lecture.getMinChangeoverTime(), lecture.getMinChangeoverTime());
        assertEquals(this.lecture.getTitle(), lecture.getTitle());
        assertEquals(this.lecture.getCourse(), lecture.getCourse());
    }
}
