package service;


import de.nordakademie.iaa.dao.SeminarDAO;
import de.nordakademie.iaa.model.Seminar;
import de.nordakademie.iaa.model.SeminarType;
import de.nordakademie.iaa.service.SeminarService;
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

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SeminarServiceTest {

    @Autowired
    private SeminarService seminarService;

    @Autowired
    private SeminarDAO seminarDAO;

    private final Long id = 1L;
    private final String title = "Seminar";
    private final SeminarType seminarType = SeminarType.ETHICS_SOCIAL;
    private final Seminar seminar = new Seminar(title, seminarType);

    @Test
    public void testSaveSeminar() {
        seminarService.saveSeminar(seminar);
        verify(seminarDAO, times(1)).save(seminar);
    }

    @Test
    public void testListSeminars() {
        seminarService.listSeminars();
        verify(seminarDAO, times(1)).findAll();
    }

    @Test
    public void testLoadSeminar() {
        seminarService.loadSeminar(id);
        verify(seminarDAO, times(1)).findOne(id);
    }

    @Test
    public void testDeleteExistingSeminar() {
        when(seminarDAO.findOne(id)).thenReturn(seminar);
        assertTrue(seminarService.deleteSeminar(id));
        verify(seminarDAO, times(1)).delete(seminar);
    }

    @Test
    public void testFindByTitle() {
        seminarService.findSeminarByTitle(title);
        verify(seminarDAO, times(1)).findByTitle(title);
    }

    @After
    public void clear() {
        Mockito.reset(seminarDAO);
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public SeminarService seminarService() {
            return new SeminarServiceImpl(seminarDAO());
        }

        @Bean
        public SeminarDAO seminarDAO() {
            return mock(SeminarDAO.class);
        }
    }
}