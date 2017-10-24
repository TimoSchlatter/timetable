package de.nordakademie.iaa.test.dao;

import de.nordakademie.iaa.ApplicationConfig;
import de.nordakademie.iaa.dao.DocentDAO;
import de.nordakademie.iaa.model.Course;
import de.nordakademie.iaa.model.Docent;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Duration;
import java.util.HashSet;

/**
 * Created by arvid on 24.10.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfig.class})
@Transactional
public class DocentDAOTest {

    @Autowired
    private DocentDAO docentDAO;
    @PersistenceContext
    private EntityManager entityManager;

    private Docent docent;

    @Before
    public void setupData() {
        HashSet<Course> courses = new HashSet<>();
        courses.add(new Course('I',123,"Test Driven Development"));
        docent = new Docent("test@docent.com", "John", "Doe", "0123123123", "Dr.Dr.", true, Duration.ofMinutes(20), courses);
        docentDAO.save(docent);
    }
/**
    @After
    public void tearDown() {
        entityManager.clear();
    }

    @Test
    public void testFindOne() {
        Docent docent = docentDAO.findOne(this.docent.getId());

        assertEquals(this.docent.getMinChangeoverTime(), docent.get());
        assertEquals(this.docent.getNumber(), docent.getNumber());
        assertEquals(this.docent.getTitle(), docent.getTitle());
    }

    @Test
    public void testFindAll() {
        List<Docent> docents = docentDAO.findAll();

        assertEquals(1, docents.size());

        for (Docent docent : docents) {
            assertEquals(this.docent.getField(), docent.getField());
            assertEquals(this.docent.getNumber(), docent.getNumber());
            assertEquals(this.docent.getTitle(), docent.getTitle());
        }
    }

    @Test
    public void testDelete() {
        docentDAO.delete(this.docent);
        List<Docent> docents = docentDAO.findAll();

        assertTrue(docents.isEmpty());

    }

    @Test
    public void testDeleteById() {
        docentDAO.deleteById(this.docent.getId());
        List<Docent> docents = docentDAO.findAll();

        assertTrue(docents.isEmpty());
    }

    @Test
    public void testFindDocentByFieldAndNumber() {
        Docent docent = docentDAO.findDocentByFieldAndNumber(this.docent.getField(), this.docent.getNumber());

        assertEquals(this.docent.getField(), docent.getField());
        assertEquals(this.docent.getNumber(), docent.getNumber());
        assertEquals(this.docent.getTitle(), docent.getTitle());
    }

    private void compareDocents(Docent docent) {
        assertEquals(this.docent.getMinChangeoverTime(), docent.getMinChangeoverTime());
        assertEquals(this.docent.getCourses(), docent.getCourses());
        assertEquals(this.docent.getEmail(), docent.getEmail());
        assertEquals(this.docent.getForename(), docent.getForename());
        assertEquals(this.docent.getSurname(), docent.getSurname());
        assertEquals(this.docent.getPhoneNumber(), docent.getPhoneNumber());
        assertEquals(this.docent.getTitle(), docent.getTitle());
    }
    **/
}
