package dao;

import de.nordakademie.iaa.ApplicationConfig;
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
 * Created by arvid on 24.10.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfig.class})
@Transactional
public class DocentDAOTest {


    private DocentDAO docentDAO;

    @PersistenceContext
    private EntityManager entityManager;

    private Docent docent;

    @Autowired
    public void setDocentDAO(DocentDAO docentDAO) {
        this.docentDAO = docentDAO;
    }

    @Before
    public void setupData() {
        docent = new Docent("test@docent.com", "John", "Doe", "0123123123", "Dr.Dr.", true, 20);
        docentDAO.save(docent);
    }

    @After
    public void tearDown() {
        entityManager.clear();
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
        for (Docent docent : docents) {
            compareDocents(docent);
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
    public void testFindDocentByName() {
        Docent docent = docentDAO.findByForenameAndSurname("John","Doe");
        compareDocents(docent);
    }

    @Test
    public void testFindDocentsByEmploymentState() {
        List<Docent> docents = docentDAO.findByPermanentlyEmployed(true);
        assertEquals(1, docents.size());
        for (Docent docent : docents) {
            compareDocents(docent);
        }
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
