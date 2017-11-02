package dao;

import com.sun.org.apache.xpath.internal.operations.Mod;
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

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by arvid on 25.10.17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Application.class})
@Transactional
public class ModuleDAOTest {


    private ModuleDAO moduleDAO;

    @PersistenceContext
    private EntityManager entityManager;

    private Module module;

    @Autowired
    public void setModuleDAO(ModuleDAO moduleDAO) {
        this.moduleDAO = moduleDAO;
    }

    @Before
    public void setupData() {
        module = new Course("Rudern ohne Paddel", "R",234 );
        moduleDAO.save(module);
    }

    @After
    public void tearDown() {
        entityManager.clear();
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
        for (Module module : modules) {
            compareModules(module);
        }
    }

    @Test
    public void testDelete() {
        moduleDAO.delete(this.module);
        List<Module> modules = moduleDAO.findAll();
        assertTrue(modules.isEmpty());

    }

    @Test
    public void testDeleteById() {
        moduleDAO.deleteById(this.module.getId());
        List<Module> modules = moduleDAO.findAll();
        assertTrue(modules.isEmpty());
    }

    @Test
    public void testFindByTitle() {
        Module module = moduleDAO.findByTitle(this.module.getTitle());
        compareModules(module);
    }

    private void compareModules(Module module) {
        assertEquals(this.module.getShortTitle(), module.getShortTitle());
        assertEquals(this.module.getTitle(), module.getTitle());
    }
}
