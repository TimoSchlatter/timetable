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

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by arvid on 24.10.17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Application.class})
@Transactional
public class ManipleDAOTest {

    private ManipleDAO manipleDAO;
    private CenturyDAO centuryDAO;

    @PersistenceContext
    private EntityManager entityManager;

    private Maniple maniple;

    @Autowired
    public void setManipleDAO(ManipleDAO manipleDAO) {
        this.manipleDAO = manipleDAO;
    }

    @Autowired
    public void setCenturyDAO(CenturyDAO centuryDAO) {
        this.centuryDAO = centuryDAO;
    }

    @Before
    public void setupData() {
        List<Century> centuries = new ArrayList<>();
        Century century = new Century("I14a",20, 42);
        centuryDAO.save(century);
        centuries.add(century);
        maniple = new Maniple("I14", 20,centuries);
        manipleDAO.save(maniple);
    }

    @After
    public void tearDown() {
        entityManager.clear();
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
        for(Maniple maniple : maniples) {
            compareManiple(maniple);
        }
    }

    @Test
    public void testDelete() {
        manipleDAO.delete(this.maniple);
        List<Maniple> maniples = manipleDAO.findAll();
        assertTrue(maniples.isEmpty());

    }

    @Test
    public void testDeleteById() {
        manipleDAO.deleteById(this.maniple.getId());
        List<Maniple> maniples = manipleDAO.findAll();
        assertTrue(maniples.isEmpty());
    }

    @Test
    public void findByName() {
        Maniple maniple = manipleDAO.findByName(this.maniple.getName());
        compareManiple(maniple);
    }

    private void compareManiple(Maniple maniple) {
        assertEquals(this.maniple.getCenturies(), maniple.getCenturies());
        assertEquals(this.maniple.getName(), maniple.getName());
        assertEquals(this.maniple.getMinChangeoverTime(), maniple.getMinChangeoverTime());
    }
}
