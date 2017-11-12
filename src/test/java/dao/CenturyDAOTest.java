package dao;

import de.nordakademie.iaa.Application;
import de.nordakademie.iaa.dao.CenturyDAO;
import de.nordakademie.iaa.model.Century;
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
 * Test class for CenturyDAO class.
 *
 * @author Arvid Ottenberg
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@Transactional
public class CenturyDAOTest {

    @Autowired
    private CenturyDAO centuryDAO;

    @PersistenceContext
    private EntityManager entityManager;

    private final Century century = new Century("I14a", 20, 42);;

    @Before
    public void setupData() {
        centuryDAO.save(century);
    }

    @Test
    public void testFindOne() {
        Century century = centuryDAO.findOne(this.century.getId());
        compareCentury(century);
    }

    @Test
    public void testFindAll() {
        List<Century> centurys = centuryDAO.findAll();
        assertEquals(1, centurys.size());
        centurys.forEach(this::compareCentury);
    }

    @Test
    public void findByName() {
        Century century = centuryDAO.findByName(this.century.getName());
        compareCentury(century);
    }

    @Test
    public void testDelete() {
        centuryDAO.delete(century);
        assertTrue(centuryDAO.findAll().isEmpty());
    }

    @After
    public void tearDown() {
        entityManager.clear();
    }

    private void compareCentury(Century century) {
        assertEquals(this.century.getNumberOfStudents(), century.getNumberOfStudents());
        assertEquals(this.century.getName(), century.getName());
        assertEquals(this.century.getMinChangeoverTime(), century.getMinChangeoverTime());
    }
}