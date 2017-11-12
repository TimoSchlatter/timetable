package dao;

import de.nordakademie.iaa.Application;
import de.nordakademie.iaa.dao.SeminarGroupDAO;
import de.nordakademie.iaa.model.SeminarGroup;
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
 * Test class for SeminarGroupDAO class.
 *
 * @author Arvid Ottenberg
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@Transactional
public class SeminarGroupDAOTest {

    @Autowired
    private SeminarGroupDAO seminarGroupDAO;

    @PersistenceContext
    private EntityManager entityManager;

    private SeminarGroup seminarGroup;

    @Before
    public void setupData() {
        seminarGroup = new SeminarGroup("Gruppe Lesen", 20, 30);
        seminarGroupDAO.save(seminarGroup);
    }

    @Test
    public void testFindOne() {
        SeminarGroup seminarGroup = seminarGroupDAO.findOne(this.seminarGroup.getId());
        compareSeminarGroups(seminarGroup);
    }

    @Test
    public void testFindAll() {
        List<SeminarGroup> seminarGroups = seminarGroupDAO.findAll();
        assertEquals(1, seminarGroups.size());
        seminarGroups.forEach(this::compareSeminarGroups);
    }

    @Test
    public void findByName() {
        SeminarGroup seminarGroup = seminarGroupDAO.findByName(this.seminarGroup.getName());
        compareSeminarGroups(seminarGroup);
    }

    @Test
    public void testDelete() {
        seminarGroupDAO.delete(seminarGroup);
        assertTrue(seminarGroupDAO.findAll().isEmpty());
    }

    @After
    public void tearDown() {
        entityManager.clear();
    }

    private void compareSeminarGroups(SeminarGroup seminarGroup) {
        assertEquals(this.seminarGroup.getMaximumNumberOfStudents(), seminarGroup.getMaximumNumberOfStudents());
        assertEquals(this.seminarGroup.getMinChangeoverTime(), seminarGroup.getMinChangeoverTime());
    }
}