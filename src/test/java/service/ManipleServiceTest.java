package service;

import de.nordakademie.iaa.dao.ManipleDAO;
import de.nordakademie.iaa.model.Maniple;
import de.nordakademie.iaa.service.ManipleService;
import de.nordakademie.iaa.service.exception.NotEnoughChangeoverTimeProvidedException;
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

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ManipleServiceTest {

    @Autowired
    private ManipleService manipleService;

    @Autowired
    private ManipleDAO manipleDAO;

    private final String name = "I";
    private final Long id = 1L;
    private final Maniple maniple = new Maniple(name);

    @Test(expected = NotEnoughChangeoverTimeProvidedException.class)
    public void testSaveManipleWithoutEnoughChangeoverTime() throws NotEnoughChangeoverTimeProvidedException {
        maniple.setMinChangeoverTime(14);
        manipleService.saveManiple(maniple);
        verify(manipleDAO, times(0)).save(maniple);
    }

    @Test
    public void testSaveManiple() throws NotEnoughChangeoverTimeProvidedException {
        maniple.setMinChangeoverTime(15);
        manipleService.saveManiple(maniple);
        verify(manipleDAO, times(1)).save(maniple);
    }

    @Test
    public void testListManiples() {
        manipleService.listManiples();
        verify(manipleDAO, times(1)).findAll();
    }

    @Test
    public void testLoadManiple() {
        manipleService.loadManiple(id);
        verify(manipleDAO, times(1)).findOne(id);
    }

    @Test
    public void testDeleteNonExistingManiple() {
        when(manipleDAO.findOne(id)).thenReturn(null);
        assertFalse(manipleService.deleteManiple(id));
        verify(manipleDAO, times(0)).deleteById(anyLong());
    }

    @Test
    public void testDeleteExistingManiple() {
        when(manipleDAO.findOne(id)).thenReturn(maniple);
        assertTrue(manipleService.deleteManiple(id));
        verify(manipleDAO, times(1)).delete(maniple);
    }

    @Test
    public void testFindByName() {
        manipleService.findByName(name);
        verify(manipleDAO, times(1)).findByName(name);
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