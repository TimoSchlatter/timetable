package dao;

import de.nordakademie.iaa.Application;
import de.nordakademie.iaa.dao.CenturyDAO;
import de.nordakademie.iaa.dao.CohortDAO;
import de.nordakademie.iaa.dao.ManipleDAO;
import de.nordakademie.iaa.model.Century;
import de.nordakademie.iaa.model.Cohort;
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
 * Created by arvid on 25.10.17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Application.class})
@Transactional
public class CohortDAOTest {

    private CohortDAO cohortDAO;
    private ManipleDAO manipleDAO;
    private CenturyDAO centuryDAO;

    @PersistenceContext
    private EntityManager entityManager;

    private Cohort cohort;

    @Autowired
    public void setCohortDAO(CohortDAO cohortDAO) {
        this.cohortDAO = cohortDAO;
    }

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
        Maniple maniple = new Maniple("I14", 20,centuries);
        manipleDAO.save(maniple);
        List<Maniple> maniples = new ArrayList<>();
        maniples.add(maniple);
        cohort = new Cohort("I",42, maniples);
        cohortDAO.save(cohort);
    }

    @After
    public void tearDown() {
        entityManager.clear();
    }

    @Test
    public void testFindOne() {
        Cohort cohort = cohortDAO.findOne(this.cohort.getId());
        compareCohort(cohort);
    }

    @Test
    public void testFindAll() {
        List<Cohort> cohorts = cohortDAO.findAll();
        assertEquals(1, cohorts.size());
        for(Cohort cohort : cohorts) {
            compareCohort(cohort);
        }
    }

    @Test
    public void findByName() {
        Cohort cohort = cohortDAO.findByName(this.cohort.getName());
        compareCohort(cohort);
    }

    @Test
    public void testDelete() {
        cohortDAO.delete(this.cohort);
        List<Cohort> cohorts = cohortDAO.findAll();
        assertTrue(cohorts.isEmpty());

    }

    @Test
    public void testDeleteById() {
        cohortDAO.deleteById(this.cohort.getId());
        List<Cohort> cohorts = cohortDAO.findAll();
        assertTrue(cohorts.isEmpty());
    }

    private void compareCohort(Cohort cohort) {
        assertEquals(this.cohort.getManiples(), cohort.getManiples());
        assertEquals(this.cohort.getName(), cohort.getName());
        assertEquals(this.cohort.getMinChangeoverTime(), cohort.getMinChangeoverTime());
    }
}
