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

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by arvid on 25.10.17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Application.class})
@Transactional
public class SeminarDAOTest {


    private SeminarDAO seminarDAO;

    @PersistenceContext
    private EntityManager entityManager;

    private Seminar seminar;

    @Autowired
    public void setSeminarDAO(SeminarDAO seminarDAO) {
        this.seminarDAO = seminarDAO;
    }

    @Before
    public void setupData() {
        seminar = new Seminar("Richtig lesen", SeminarType.KEY_QUALIFICATION);
        seminarDAO.save(seminar);
    }

    @After
    public void tearDown() {
        entityManager.clear();
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
        for (Seminar seminar : seminars) {
            compareSeminars(seminar);
        }
    }

    @Test
    public void testFindByTitle() {
        Seminar seminar = seminarDAO.findByTitle(this.seminar.getTitle());
        compareSeminars(seminar);
    }

    @Test
    public void testDelete() {
        seminarDAO.delete(this.seminar);
        List<Seminar> seminars = seminarDAO.findAll();
        assertTrue(seminars.isEmpty());

    }

    @Test
    public void testDeleteById() {
        seminarDAO.deleteById(this.seminar.getId());
        List<Seminar> seminars = seminarDAO.findAll();
        assertTrue(seminars.isEmpty());
    }

    private void compareSeminars(Seminar seminar) {
        assertEquals(this.seminar.getSeminarType(), seminar.getSeminarType());
        assertEquals(this.seminar.getTitle(), seminar.getTitle());
        assertEquals(this.seminar.getShortTitle(), seminar.getShortTitle());
    }
}
