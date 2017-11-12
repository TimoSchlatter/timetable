package dao;

import de.nordakademie.iaa.Application;
import de.nordakademie.iaa.dao.SeminarDAO;
import de.nordakademie.iaa.model.Seminar;
import de.nordakademie.iaa.model.SeminarType;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for SeminarDAO class.
 *
 * @author Arvid Ottenberg
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@Transactional
public class SeminarDAOTest {

    @Autowired
    private SeminarDAO seminarDAO;

    @PersistenceContext
    private EntityManager entityManager;

    private Seminar seminar;

    @Before
    public void setupData() {
        seminar = new Seminar("Richtig lesen", SeminarType.KEY_QUALIFICATION);
        seminarDAO.save(seminar);
    }

    @Test
    public void testFindOne() {
        Seminar seminar = seminarDAO.findOne(this.seminar.getId());
        compareSeminars(seminar);
    }

    @Test
    public void testFindAll() {
        List<Seminar> seminars = seminarDAO.findAll();
        assertEquals(1, seminars.size());
        seminars.forEach(this::compareSeminars);
    }

    @Test
    public void testFindByTitle() {
        Seminar seminar = seminarDAO.findByTitle(this.seminar.getTitle());
        compareSeminars(seminar);
    }

    @Test
    public void testDelete() {
        seminarDAO.delete(seminar);
        assertTrue(seminarDAO.findAll().isEmpty());
    }

    @After
    public void tearDown() {
        entityManager.clear();
    }

    private void compareSeminars(Seminar seminar) {
        assertEquals(this.seminar.getSeminarType(), seminar.getSeminarType());
        assertEquals(this.seminar.getTitle(), seminar.getTitle());
        assertEquals(this.seminar.getShortTitle(), seminar.getShortTitle());
    }
}