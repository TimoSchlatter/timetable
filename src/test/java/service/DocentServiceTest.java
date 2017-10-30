package service;


import de.nordakademie.iaa.dao.DocentDAO;
import de.nordakademie.iaa.model.Docent;
import de.nordakademie.iaa.service.DocentService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
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

import static org.mockito.Matchers.anyLong;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class DocentServiceTest {

    @Autowired
    private DocentService docentService;

    @Autowired
    private DocentDAO docentDAO;

    private final String forename = "John";
    private final String surname = "Doe";
    private final String email = "test@docent.com";
    private final String phoneNumber = "0123123123";
    private final String title = "Dr.Dr.";
    private int minChangeoverTime = 20;
    private final boolean isPermanentlyEmployed = true;
    private final Docent docent = new Docent(email, forename, surname, phoneNumber, title, isPermanentlyEmployed, minChangeoverTime);

    @Test
    public void testSaveDocent() {
        docentService.saveDocent(docent);
        Mockito.verify(docentDAO, times(1)).save(docent);
    }

    @Test
    public void testListDocents() {
        docentService.listDocents();
        Mockito.verify(docentDAO, times(1)).findAll();
    }

    @Test
    public void testLoadDocent() {
        final Long id = 123L;
        docentService.loadDocent(id);
        Mockito.verify(docentDAO, times(1)).findOne(id);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteNonExistingDocent() throws EntityNotFoundException {
        final Long id = 123L;
        Mockito.when(docentDAO.findOne(anyLong())).thenReturn(null);
        docentService.deleteDocent(id);
    }

    @Test
    public void testDeleteExistingDocent() throws EntityNotFoundException {
        final Long id = 123L;
        Mockito.when(docentDAO.findOne(anyLong())).thenReturn(docent);
        docentService.deleteDocent(id);
        Mockito.verify(docentDAO, times(1)).findOne(id);
        Mockito.verify(docentDAO, times(1)).delete(docent);
    }

    @After
    public void clear() {
        Mockito.reset(docentDAO);
    }

    @Configuration
    static class AccountServiceTestContextConfiguration {

        @Bean
        public DocentService docentService() {
            return new DocentServiceImpl(docentDAO());
        }

        @Bean
        public DocentDAO docentDAO() {
            return Mockito.mock(DocentDAO.class);
        }
    }
}