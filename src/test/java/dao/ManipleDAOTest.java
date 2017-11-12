package dao;

import de.nordakademie.iaa.Application;
import de.nordakademie.iaa.dao.CenturyDAO;
import de.nordakademie.iaa.dao.ManipleDAO;
import de.nordakademie.iaa.model.Century;
import de.nordakademie.iaa.model.Maniple;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for ManipleDAO class.
 *
 * @author Arvid Ottenberg
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@Transactional
public class ManipleDAOTest {

    @Autowired
    private ManipleDAO manipleDAO;

    @Autowired
    private CenturyDAO centuryDAO;

    @PersistenceContext
    private EntityManager entityManager;

    private Maniple maniple;

    @Before
    public void setupData() {
        List<Century> centuries = new ArrayList<>();
        Century century = new Century("I14a", 20, 42);
        centuryDAO.save(century);
        centuries.add(century);
        maniple = new Maniple("I14", 20, centuries);
        manipleDAO.save(maniple);
    }

    @Test
    public void testFindOne() {
        Maniple maniple = manipleDAO.findOne(this.maniple.getId());
        compareManiple(maniple);
    }

    @Test
    public void testFindAll() {
        List<Maniple> maniples = manipleDAO.findAll();
        assertEquals(1, maniples.size());
        maniples.forEach(this::compareManiple);
    }

    @Test
    public void findByName() {
        Maniple maniple = manipleDAO.findByName(this.maniple.getName());
        compareManiple(maniple);
    }

    @Test
    public void testDelete() {
        manipleDAO.delete(maniple);
        assertTrue(manipleDAO.findAll().isEmpty());
    }

    @After
    public void tearDown() {
        entityManager.clear();
    }

    private void compareManiple(Maniple maniple) {
        assertEquals(this.maniple.getCenturies(), maniple.getCenturies());
        assertEquals(this.maniple.getName(), maniple.getName());
        assertEquals(this.maniple.getMinChangeoverTime(), maniple.getMinChangeoverTime());
    }
}