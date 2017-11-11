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

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by arvid on 24.10.17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Application.class})
@Transactional
public class CenturyDAOTest {
    
    private CenturyDAO centuryDAO;
    
    @PersistenceContext
    private EntityManager entityManager;

    private Century century;

    @Autowired
    public void setCenturyDAO(CenturyDAO centuryDAO) {
        this.centuryDAO = centuryDAO;
    }
        
    @Before
    public void setupData() {
        century = new Century("I14a",20, 42);
        centuryDAO.save(century);
    }

    @After
    public void tearDown() {
        entityManager.clear();
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
        for(Century century : centurys) {
            compareCentury(century);
        }
    }

    @Test
    public void findByName() {
        Century century = centuryDAO.findByName(this.century.getName());
        compareCentury(century);
    }

    @Test
    public void testDelete() {
        centuryDAO.delete(this.century);
        List<Century> centurys = centuryDAO.findAll();
        assertTrue(centurys.isEmpty());

    }

    private void compareCentury(Century century) {
        assertEquals(this.century.getNumberOfStudents(), century.getNumberOfStudents());
        assertEquals(this.century.getName(), century.getName());
        assertEquals(this.century.getMinChangeoverTime(), century.getMinChangeoverTime());
    }
}
