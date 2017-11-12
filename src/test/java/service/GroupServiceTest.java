package service;

import de.nordakademie.iaa.dao.GroupDAO;
import de.nordakademie.iaa.service.GroupService;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Test class for GroupService class.
 *
 * @author Timo Schlatter
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class GroupServiceTest {

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupDAO groupDAO;

    @Test
    public void testLoadGroup() {
        Long id = 1L;
        groupService.loadGroup(id);
        verify(groupDAO, times(1)).findOne(id);
    }

    @After
    public void reset() {
        Mockito.reset(groupDAO);
    }

    @Configuration
    static class TestConfiguration {

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