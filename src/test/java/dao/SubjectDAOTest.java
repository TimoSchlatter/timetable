package dao;

import de.nordakademie.iaa.Application;
import de.nordakademie.iaa.dao.CourseDAO;
import de.nordakademie.iaa.dao.SubjectDAO;
import de.nordakademie.iaa.model.Course;
import de.nordakademie.iaa.model.Subject;
import de.nordakademie.iaa.model.SubjectType;
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
 * Created by arvid on 24.10.17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@Transactional
public class SubjectDAOTest {

    private SubjectDAO subjectDAO;
    private CourseDAO courseDAO;

    @PersistenceContext
    private EntityManager entityManager;

    private Subject subject;

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
        Course course = new Course("I",123,"Treppensteigen III");
        courseDAO.save(course);
        subject = new Subject(20, SubjectType.LECTURE, course);
        subjectDAO.save(subject);
    }

    @After
    public void tearDown() {
        entityManager.clear();
    }

    @Test
    public void testFindOne() {
        Subject subject = subjectDAO.findOne(this.subject.getId());
        compareSubjects(subject);
    }

    @Test
    public void testFindAll() {
        List<Subject> subjects = subjectDAO.findAll();
        assertEquals(1, subjects.size());
        subjects.forEach(this::compareSubjects);
    }

    @Test
    public void testDelete() {
        subjectDAO.delete(this.subject);
        List<Subject> subjects = subjectDAO.findAll();
        assertTrue(subjects.isEmpty());
    }

    @Test
    public void testDeleteById() {
        subjectDAO.deleteById(this.subject.getId());
        List<Subject> subjects = subjectDAO.findAll();
        assertTrue(subjects.isEmpty());
    }

    @Test
    public void testFindSubjectsByType() {
        List<Subject> subjects = subjectDAO.findBySubjectType(this.subject.getSubjectType());
        assertEquals(1, subjects.size());
        subjects.forEach(this::compareSubjects);
    }

    private void compareSubjects(Subject subject) {
        assertEquals(this.subject.getModule(), subject.getModule());
        assertEquals(this.subject.getMinChangeoverTime(), subject.getMinChangeoverTime());
        assertEquals(this.subject.getSubjectType(), subject.getSubjectType());
    }
}
