package service;


import de.nordakademie.iaa.dao.ManipleDAO;
import de.nordakademie.iaa.model.Maniple;
import de.nordakademie.iaa.service.ManipleService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import de.nordakademie.iaa.service.impl.ManipleServiceImpl;
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
public class ManipleServiceTest {

    @Autowired
    private ManipleService manipleService;

    @Autowired
    private ManipleDAO manipleDAO;

    private final String name = "I";
    private final Maniple maniple = new Maniple(name);

    @Test
    public void testSaveManiple() {
        manipleService.saveManiple(maniple);
        Mockito.verify(manipleDAO, times(1)).save(maniple);
    }

    @Test
    public void testListManiples() {
        manipleService.listManiples();
        Mockito.verify(manipleDAO, times(1)).findAll();
    }

    @Test
    public void testLoadManiple() {
        final Long id = 123L;
        manipleService.loadManiple(id);
        Mockito.verify(manipleDAO, times(1)).findOne(id);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteNonExistingManiple() throws EntityNotFoundException {
        final Long id = 123L;
        Mockito.when(manipleDAO.findOne(anyLong())).thenReturn(null);
        manipleService.deleteManiple(id);
    }

    @Test
    public void testDeleteExistingManiple() throws EntityNotFoundException {
        final Long id = 123L;
        Mockito.when(manipleDAO.findOne(anyLong())).thenReturn(maniple);
        manipleService.deleteManiple(id);
        Mockito.verify(manipleDAO, times(1)).findOne(id);
        Mockito.verify(manipleDAO, times(1)).delete(maniple);
    }

    @After
    public void clear() {
        Mockito.reset(manipleDAO);
    }

    @Configuration
    static class AccountServiceTestContextConfiguration {

        @Bean
        public ManipleService manipleService() {
            return new ManipleServiceImpl(manipleDAO());
        }

        @Bean
        public ManipleDAO manipleDAO() {
            return Mockito.mock(ManipleDAO.class);
        }
    }
}