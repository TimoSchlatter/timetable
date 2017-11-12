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
 * Test class for SubjectDAO class.
 *
 * @author Arvid Ottenberg
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@Transactional
public class SubjectDAOTest {

    @Autowired
    private SubjectDAO subjectDAO;

    @Autowired
    private CourseDAO courseDAO;

    @PersistenceContext
    private EntityManager entityManager;

    private Subject subject;
    private Course course;

    @Before
    public void setupData() {
        course = new Course("Treppensteigen III", "I", 123);
        courseDAO.save(course);
        subject = new Subject(20, SubjectType.LECTURE, course);
        subjectDAO.save(subject);
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
    public void testFindBySubjectTypeAndModule() {
        Subject subject = subjectDAO.findBySubjectTypeAndModule(this.subject.getSubjectType(), this.subject.getModule());
        compareSubjects(subject);
    }

    @Test
    public void testDelete() {
        subjectDAO.delete(subject);
        assertTrue(subjectDAO.findAll().isEmpty());
    }

    @Test
    public void testDeleteByModule() {
        subjectDAO.deleteByModule(course);
        assertTrue(subjectDAO.findAll().isEmpty());
    }

    @After
    public void tearDown() {
        entityManager.clear();
    }

    private void compareSubjects(Subject subject) {
        assertEquals(this.subject.getModule(), subject.getModule());
        assertEquals(this.subject.getMinChangeoverTime(), subject.getMinChangeoverTime());
        assertEquals(this.subject.getSubjectType(), subject.getSubjectType());
    }
}