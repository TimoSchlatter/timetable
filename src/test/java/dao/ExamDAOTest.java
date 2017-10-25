package dao;

import de.nordakademie.iaa.ApplicationConfig;
import de.nordakademie.iaa.dao.CourseDAO;
import de.nordakademie.iaa.dao.ExamDAO;
import de.nordakademie.iaa.model.Course;
import de.nordakademie.iaa.model.Exam;
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
public class ExamDAOTest {


    private ExamDAO examDAO;
    private CourseDAO courseDAO;

    @PersistenceContext
    private EntityManager entityManager;

    private Exam exam;

    @Autowired
    public void setExamDAO(ExamDAO examDAO) {
        this.examDAO = examDAO;
    }

    @Autowired
    public void setCourseDAO(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    @Before
    public void setupData() {
        Course course = new Course('X', 123, "Testen");
        exam = new Exam(30, course);
        courseDAO.save(course);
        exam = new Exam(20, course);
        examDAO.save(exam);
    }

    @After
    public void tearDown() {
        entityManager.clear();
    }

    @Test
    public void testFindOne() {
        Exam exam = examDAO.findOne(this.exam.getId());
        compareExams(exam);
    }

    @Test
    public void testFindAll() {
        List<Exam> exams = examDAO.findAll();
        assertEquals(1, exams.size());
        for (Exam exam : exams) {
            compareExams(exam);
        }
    }

    @Test
    public void testDelete() {
        examDAO.delete(this.exam);
        List<Exam> exams = examDAO.findAll();
        assertTrue(exams.isEmpty());

    }

    @Test
    public void testDeleteById() {
        examDAO.deleteById(this.exam.getId());
        List<Exam> exams = examDAO.findAll();
        assertTrue(exams.isEmpty());
    }

    private void compareExams(Exam exam) {
        assertEquals(this.exam.getMinChangeoverTime(), exam.getMinChangeoverTime());
        assertEquals(this.exam.getTitle(), exam.getTitle());
        assertEquals(this.exam.getCourse(), exam.getCourse());
    }
}

