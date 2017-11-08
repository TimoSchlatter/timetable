package service;


import de.nordakademie.iaa.dao.CohortDAO;
import de.nordakademie.iaa.model.Cohort;
import de.nordakademie.iaa.service.CohortService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import de.nordakademie.iaa.service.exception.NotEnoughChangeoverTimeProvidedException;
import de.nordakademie.iaa.service.impl.CohortServiceImpl;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CohortServiceTest {

    @Autowired
    private CohortService cohortService;

    @Autowired
    private CohortDAO cohortDAO;

    private final String name = "A";
    private final int numberOfStudents = 42;
    private final Cohort cohort = new Cohort(name, numberOfStudents);

    @Test(expected = NotEnoughChangeoverTimeProvidedException.class)
    public void testSaveCohortWithoutEnoughChangeoverTime() throws NotEnoughChangeoverTimeProvidedException {
        cohort.setMinChangeoverTime(14);
        cohortService.saveCohort(cohort);
        verify(cohortDAO, times(0)).save(cohort);
    }

    @Test
    public void testSaveCohort() throws NotEnoughChangeoverTimeProvidedException {
        cohort.setMinChangeoverTime(15);
        cohortService.saveCohort(cohort);
        verify(cohortDAO, times(1)).save(cohort);
    }

    @Test
    public void testListCohorts() {
        cohortService.listCohorts();
        verify(cohortDAO, times(1)).findAll();
    }

    @Test
    public void testLoadCohort() {
        final Long id = 123L;
        cohortService.loadCohort(id);
        verify(cohortDAO, times(1)).findOne(id);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteNonExistingCohort() throws EntityNotFoundException {
        final Long id = 123L;
        when(cohortDAO.findOne(anyLong())).thenReturn(null);
        cohortService.deleteCohort(id);
    }

    @Test
    public void testDeleteExistingCohort() throws EntityNotFoundException {
        final Long id = 123L;
        when(cohortDAO.findOne(anyLong())).thenReturn(cohort);
        cohortService.deleteCohort(id);
        verify(cohortDAO, times(1)).findOne(id);
        verify(cohortDAO, times(1)).delete(cohort);
    }

    @Test
    public void testFindByName() {
        cohortService.findByName(name);
        verify(cohortDAO, times(1)).findByName(name);
    }

    @After
    public void reset() {
        Mockito.reset(cohortDAO);
    }

    @Configuration
    static class AccountServiceTestContextConfiguration {

        @Bean
        public CohortService cohortService() {
            return new CohortServiceImpl(cohortDAO());
        }

        @Bean
        public CohortDAO cohortDAO() {
            return mock(CohortDAO.class);
        }
    }
}
