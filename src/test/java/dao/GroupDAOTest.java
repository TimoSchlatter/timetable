package dao;

import de.nordakademie.iaa.Application;
import de.nordakademie.iaa.dao.GroupDAO;
import de.nordakademie.iaa.model.Century;
import de.nordakademie.iaa.model.Group;
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
 * Test class for GroupDAO class.
 *
 * @author Arvid Ottenberg
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@Transactional
public class GroupDAOTest {

    @Autowired
    private GroupDAO groupDAO;

    @PersistenceContext
    private EntityManager entityManager;

    private Group group;

    @Before
    public void setupData() {
        group = new Century("I14a", 20, 42);
        groupDAO.save(group);
    }

    @Test
    public void testFindOne() {
        Group group = groupDAO.findOne(this.group.getId());
        compareGroups(group);
    }

    @Test
    public void testFindAll() {
        List<Group> groups = groupDAO.findAll();
        assertEquals(1, groups.size());
        groups.forEach(this::compareGroups);
    }

    @Test
    public void testDelete() {
        groupDAO.delete(group);
        assertTrue(groupDAO.findAll().isEmpty());
    }

    @After
    public void tearDown() {
        entityManager.clear();
    }

    private void compareGroups(Group group) {
        assertEquals(this.group.getMinChangeoverTime(), group.getMinChangeoverTime());
        assertEquals(this.group.getName(), group.getName());
    }
}

