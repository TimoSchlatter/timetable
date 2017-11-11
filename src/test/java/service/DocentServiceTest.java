package service;


import de.nordakademie.iaa.dao.DocentDAO;
import de.nordakademie.iaa.model.Docent;
import de.nordakademie.iaa.service.DocentService;
import de.nordakademie.iaa.service.exception.NotEnoughChangeoverTimeProvidedException;
import de.nordakademie.iaa.service.impl.DocentServiceImpl;
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
public class DocentServiceTest {

    @Autowired
    private DocentService docentService;

    @Autowired
    private DocentDAO docentDAO;

    private final Long id = 1L;
    private final String forename = "John";
    private final String surname = "Doe";
    private final String email = "test@docent.com";
    private final String phoneNumber = "0123123123";
    private final String title = "Dr.Dr.";
    private final int minChangeoverTime = 20;
    private final boolean isPermanentlyEmployed = true;
    private final Docent docent = new Docent(email, forename, surname, phoneNumber, title, isPermanentlyEmployed, minChangeoverTime);

    @Test(expected = NotEnoughChangeoverTimeProvidedException.class)
    public void testSaveDocentWithoutEnoughChangeoverTime() throws NotEnoughChangeoverTimeProvidedException {
        docent.setMinChangeoverTime(14);
        docentService.saveDocent(docent);
        verify(docentDAO, times(0)).save(docent);
    }

    @Test
    public void testSaveDocent() throws NotEnoughChangeoverTimeProvidedException {
        docent.setMinChangeoverTime(15);
        docentService.saveDocent(docent);
        verify(docentDAO, times(1)).save(docent);
    }

    @Test
    public void testListDocents() {
        docentService.listDocents();
        verify(docentDAO, times(1)).findAll();
    }

    @Test
    public void testLoadDocent() {
        docentService.loadDocent(id);
        verify(docentDAO, times(1)).findOne(id);
    }

    @Test
    public void testDeleteExistingDocent() {
        when(docentDAO.findOne(id)).thenReturn(docent);
        assertTrue(docentService.deleteDocent(id));
        verify(docentDAO, times(1)).delete(docent);
    }

    @Test
    public void testFindByForenameAndSurname() {
        docentService.findByForenameAndSurname(forename, surname);
        verify(docentDAO, times(1)).findByForenameAndSurname(forename, surname);
    }

    @After
    public void reset() {
        Mockito.reset(docentDAO);
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public DocentService docentService() {
            return new DocentServiceImpl(docentDAO());
        }

        @Bean
        public DocentDAO docentDAO() {
            return mock(DocentDAO.class);
        }
    }
}
