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
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for CohortDAO class.
 *
 * @author Arvid Ottenberg
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@Transactional
public class CohortDAOTest {

    @Autowired
    private CohortDAO cohortDAO;

    @Autowired
    private ManipleDAO manipleDAO;

    @Autowired
    private CenturyDAO centuryDAO;

    @PersistenceContext
    private EntityManager entityManager;

    private Cohort cohort;

    @Before
    public void setupData() {
        Century century = new Century("I14a", 20, 42);
        centuryDAO.save(century);
        Maniple maniple = new Maniple("I14", 20, Arrays.asList(century));
        manipleDAO.save(maniple);
        cohort = new Cohort("I", 42, Arrays.asList(maniple));
        cohortDAO.save(cohort);
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
        cohorts.forEach(this::compareCohort);
    }

    @Test
    public void findByName() {
        Cohort cohort = cohortDAO.findByName(this.cohort.getName());
        compareCohort(cohort);
    }

    @Test
    public void testDelete() {
        cohortDAO.delete(cohort);
        assertTrue(cohortDAO.findAll().isEmpty());
    }

    @After
    public void tearDown() {
        entityManager.clear();
    }

    private void compareCohort(Cohort cohort) {
        assertEquals(this.cohort.getManiples(), cohort.getManiples());
        assertEquals(this.cohort.getName(), cohort.getName());
        assertEquals(this.cohort.getMinChangeoverTime(), cohort.getMinChangeoverTime());
    }
}