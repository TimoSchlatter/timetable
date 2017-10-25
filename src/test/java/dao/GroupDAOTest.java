package dao;

import de.nordakademie.iaa.ApplicationConfig;
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
 * Created by arvid on 25.10.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfig.class})
@Transactional
public class GroupDAOTest {


    private GroupDAO groupDAO;

    @PersistenceContext
    private EntityManager entityManager;

    private Group group;

    @Autowired
    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    @Before
    public void setupData() {
        group = new Century("I14a",20, 42);
        groupDAO.save(group);
    }

    @After
    public void tearDown() {
        entityManager.clear();
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
        for (Group group : groups) {
            compareGroups(group);
        }
    }

    @Test
    public void testDelete() {
        groupDAO.delete(this.group);
        List<Group> groups = groupDAO.findAll();
        assertTrue(groups.isEmpty());

    }

    @Test
    public void testDeleteById() {
        groupDAO.deleteById(this.group.getId());
        List<Group> groups = groupDAO.findAll();
        assertTrue(groups.isEmpty());
    }

    private void compareGroups(Group group) {
        assertEquals(this.group.getMinChangeoverTime(), group.getMinChangeoverTime());
        assertEquals(this.group.getName(), group.getName());
    }
}

