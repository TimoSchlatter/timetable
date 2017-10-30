package service;


import de.nordakademie.iaa.dao.SeminarDAO;
import de.nordakademie.iaa.model.Seminar;
import de.nordakademie.iaa.service.SeminarService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import de.nordakademie.iaa.service.impl.SeminarServiceImpl;
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
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SeminarServiceTest {

    @Autowired
    private SeminarService seminarService;

    @Autowired
    private SeminarDAO seminarDAO;

    private final String title = "Seminar";
    private final int maxNumberOfParticipants = 20;
    private final Seminar seminar = new Seminar(title, maxNumberOfParticipants);

    @Test
    public void testSaveSeminar() {
        seminarService.saveSeminar(seminar);
        Mockito.verify(seminarDAO, times(1)).save(seminar);
    }

    @Test
    public void testListSeminars() {
        seminarService.listSeminars();
        Mockito.verify(seminarDAO, times(1)).findAll();
    }

    @Test
    public void testLoadSeminar() {
        final Long id = 123L;
        seminarService.loadSeminar(id);
        Mockito.verify(seminarDAO, times(1)).findOne(id);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteNonExistingSeminar() throws EntityNotFoundException {
        final Long id = 123L;
        Mockito.when(seminarDAO.findOne(anyLong())).thenReturn(null);
        seminarService.deleteSeminar(id);
    }

    @Test
    public void testDeleteExistingSeminar() throws EntityNotFoundException {
        final Long id = 123L;
        Mockito.when(seminarDAO.findOne(anyLong())).thenReturn(seminar);
        seminarService.deleteSeminar(id);
        Mockito.verify(seminarDAO, times(1)).findOne(id);
        Mockito.verify(seminarDAO, times(1)).delete(seminar);
    }

    @After
    public void clear() {
        Mockito.reset(seminarDAO);
    }

    @Configuration
    static class AccountServiceTestContextConfiguration {

        @Bean
        public SeminarService seminarService() {
            return new SeminarServiceImpl(seminarDAO());
        }

        @Bean
        public SeminarDAO seminarDAO() {
            return Mockito.mock(SeminarDAO.class);
        }
    }
}