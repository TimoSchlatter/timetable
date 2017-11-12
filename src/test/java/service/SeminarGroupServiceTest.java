package service;

import de.nordakademie.iaa.dao.SeminarGroupDAO;
import de.nordakademie.iaa.model.SeminarGroup;
import de.nordakademie.iaa.service.SeminarGroupService;
import de.nordakademie.iaa.service.exception.NotEnoughChangeoverTimeProvidedException;
import de.nordakademie.iaa.service.impl.SeminarGroupServiceImpl;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Test class for SeminarGroupService class.
 *
 * @author Timo Schlatter
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SeminarGroupServiceTest {

    @Autowired
    private SeminarGroupService seminarGroupService;

    @Autowired
    private SeminarGroupDAO seminarGroupDAO;

    private final String name = "Max. Teilnehmer 10";
    private final Long id = 1L;
    private final int numberOfStudents = 42;
    private final int minChangeOverTime = 20;
    private final SeminarGroup seminarGroup = new SeminarGroup(name,minChangeOverTime, numberOfStudents);

    @Test
    public void testSaveSeminarGroup() throws NotEnoughChangeoverTimeProvidedException {
        seminarGroup.setMinChangeoverTime(15);
        seminarGroupService.saveSeminarGroup(seminarGroup);
        verify(seminarGroupDAO, times(1)).save(seminarGroup);
    }

    @Test
    public void testListSeminarGroups() {
        seminarGroupService.listSeminarGroups();
        verify(seminarGroupDAO, times(1)).findAll();
    }

    @Test
    public void testLoadSeminarGroup() {
        seminarGroupService.loadSeminarGroup(id);
        verify(seminarGroupDAO, times(1)).findOne(id);
    }

    @Test
    public void testDeleteExistingSeminarGroup() {
        when(seminarGroupDAO.findOne(id)).thenReturn(seminarGroup);
        assertTrue(seminarGroupService.deleteSeminarGroup(id));
        verify(seminarGroupDAO, times(1)).delete(seminarGroup);
    }

    @Test
    public void testFindByName() {
        seminarGroupService.findSeminarGroupByName(name);
        verify(seminarGroupDAO, times(1)).findByName(name);
    }

    @After
    public void reset() {
        Mockito.reset(seminarGroupDAO);
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public SeminarGroupService seminarGroupService() {
            return new SeminarGroupServiceImpl(seminarGroupDAO());
        }

        @Bean
        public SeminarGroupDAO seminarGroupDAO() {
            return mock(SeminarGroupDAO.class);
        }
    }
}