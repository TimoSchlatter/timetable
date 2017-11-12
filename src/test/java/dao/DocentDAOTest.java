package dao;

import de.nordakademie.iaa.Application;
import de.nordakademie.iaa.dao.DocentDAO;
import de.nordakademie.iaa.model.Docent;
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
 * Test class for DocentDAO class.
 *
 * @author Arvid Ottenberg
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@Transactional
public class DocentDAOTest {

    @Autowired
    private DocentDAO docentDAO;

    @PersistenceContext
    private EntityManager entityManager;

    private Docent docent;

    @Before
    public void setupData() {
        docent = new Docent("test@docent.com", "John", "Doe", "0123123123", "Dr.Dr.", true, 20);
        docentDAO.save(docent);
    }

    @Test
    public void testFindOne() {
        Docent docent = docentDAO.findOne(this.docent.getId());
        compareDocents(docent);
    }

    @Test
    public void testFindAll() {
        List<Docent> docents = docentDAO.findAll();
        assertEquals(1, docents.size());
        docents.forEach(this::compareDocents);
    }

    @Test
    public void testFindDocentByName() {
        Docent docent = docentDAO.findByForenameAndSurname("John", "Doe");
        compareDocents(docent);
    }

    @Test
    public void testDelete() {
        docentDAO.delete(docent);
        assertTrue(docentDAO.findAll().isEmpty());
    }

    @After
    public void tearDown() {
        entityManager.clear();
    }

    private void compareDocents(Docent docent) {
        assertEquals(this.docent.getMinChangeoverTime(), docent.getMinChangeoverTime());
        assertEquals(this.docent.getEmail(), docent.getEmail());
        assertEquals(this.docent.getForename(), docent.getForename());
        assertEquals(this.docent.getSurname(), docent.getSurname());
        assertEquals(this.docent.getPhoneNumber(), docent.getPhoneNumber());
        assertEquals(this.docent.getTitle(), docent.getTitle());
    }
}