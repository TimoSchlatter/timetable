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

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by arvid on 25.10.17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Application.class})
@Transactional
public class SeminarGroupDAOTest {


    private SeminarGroupDAO seminarGroupDAO;

    @PersistenceContext
    private EntityManager entityManager;

    private SeminarGroup seminarGroup;

    @Autowired
    public void setSeminarGroupDAO(SeminarGroupDAO seminarGroupDAO) {
        this.seminarGroupDAO = seminarGroupDAO;
    }

    @Before
    public void setupData() {
        seminarGroup = new SeminarGroup("Gruppe Lesen", 20, 30);
        seminarGroupDAO.save(seminarGroup);
    }

    @After
    public void tearDown() {
        entityManager.clear();
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
        for (SeminarGroup seminarGroup : seminarGroups) {
            compareSeminarGroups(seminarGroup);
        }
    }

    @Test
    public void findByName() {
        SeminarGroup seminarGroup = seminarGroupDAO.findByName(this.seminarGroup.getName());
        compareSeminarGroups(seminarGroup);
    }

    @Test
    public void testDelete() {
        seminarGroupDAO.delete(this.seminarGroup);
        List<SeminarGroup> seminarGroups = seminarGroupDAO.findAll();
        assertTrue(seminarGroups.isEmpty());

    }

    private void compareSeminarGroups(SeminarGroup seminarGroup) {
        assertEquals(this.seminarGroup.getMaximumNumberOfStudents(), seminarGroup.getMaximumNumberOfStudents());
        assertEquals(this.seminarGroup.getMinChangeoverTime(), seminarGroup.getMinChangeoverTime());
    }
}
