package service;


import de.nordakademie.iaa.dao.GroupDAO;
import de.nordakademie.iaa.model.Century;
import de.nordakademie.iaa.model.Group;
import de.nordakademie.iaa.service.GroupService;
import de.nordakademie.iaa.service.exception.NotEnoughChangeoverTimeProvidedException;
import de.nordakademie.iaa.service.impl.GroupServiceImpl;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class GroupServiceTest {

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupDAO groupDAO;

    private final String name = "A";
    private final int numberOfStudents = 42;
    private final Long id = 1L;
    private final Group group = new Century(name, numberOfStudents);

    @Test
    public void testSaveGroup() throws NotEnoughChangeoverTimeProvidedException {
        group.setMinChangeoverTime(15);
        groupService.saveGroup(group);
        verify(groupDAO, times(1)).save(group);
    }

    @Test
    public void testListGroups() {
        groupService.listGroups();
        verify(groupDAO, times(1)).findAll();
    }

    @Test
    public void testLoadGroup() {
        groupService.loadGroup(id);
        verify(groupDAO, times(1)).findOne(id);
    }

    @Test
    public void testDeleteNonExistingGroup() {
        when(groupDAO.findOne(id)).thenReturn(null);
        assertFalse(groupService.deleteGroup(id));
        verify(groupDAO, times(0)).deleteById(anyLong());
    }

    @Test
    public void testDeleteExistingGroup() {
        when(groupDAO.findOne(id)).thenReturn(group);
        assertTrue(groupService.deleteGroup(id));
        verify(groupDAO, times(1)).delete(group);
    }

    @After
    public void reset() {
        Mockito.reset(groupDAO);
    }

    @Configuration
    static class AccountServiceTestContextConfiguration {

        @Bean
        public GroupService groupService() {
            return new GroupServiceImpl(groupDAO());
        }

        @Bean
        public GroupDAO groupDAO() {
            return mock(GroupDAO.class);
        }
    }
}
