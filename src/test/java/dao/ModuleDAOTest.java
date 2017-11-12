package dao;

import de.nordakademie.iaa.Application;
import de.nordakademie.iaa.dao.ModuleDAO;
import de.nordakademie.iaa.model.Course;
import de.nordakademie.iaa.model.Module;
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
 * Test class for ModuleDAO class.
 *
 * @author Arvid Ottenberg
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@Transactional
public class ModuleDAOTest {

    @Autowired
    private ModuleDAO moduleDAO;

    @PersistenceContext
    private EntityManager entityManager;

    private Module module;

    @Before
    public void setupData() {
        module = new Course("Rudern ohne Paddel", "R", 234);
        moduleDAO.save(module);
    }

    @Test
    public void testFindOne() {
        Module module = moduleDAO.findOne(this.module.getId());
        compareModules(module);
    }

    @Test
    public void testFindAll() {
        List<Module> modules = moduleDAO.findAll();
        assertEquals(1, modules.size());
        modules.forEach(this::compareModules);
    }

    @Test
    public void testFindByTitle() {
        Module module = moduleDAO.findByTitle(this.module.getTitle());
        compareModules(module);
    }

    @Test
    public void testDelete() {
        moduleDAO.delete(module);
        assertTrue(moduleDAO.findAll().isEmpty());
    }

    @After
    public void tearDown() {
        entityManager.clear();
    }

    private void compareModules(Module module) {
        assertEquals(this.module.getShortTitle(), module.getShortTitle());
        assertEquals(this.module.getTitle(), module.getTitle());
    }
}
