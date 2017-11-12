package service;

import de.nordakademie.iaa.dao.CenturyDAO;
import de.nordakademie.iaa.model.Century;
import de.nordakademie.iaa.service.CenturyService;
import de.nordakademie.iaa.service.exception.NotEnoughChangeoverTimeProvidedException;
import de.nordakademie.iaa.service.impl.CenturyServiceImpl;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Test class for CenturyService class.
 *
 * @author Timo Schlatter
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CenturyServiceTest {

    @Autowired
    private CenturyService centuryService;

    @Autowired
    private CenturyDAO centuryDAO;

    private final String name = "A";
    private final int numberOfStudents = 42;
    private final Long id = 1L;
    private final Century century = new Century(name, numberOfStudents);

    @Test(expected = NotEnoughChangeoverTimeProvidedException.class)
    public void testSaveCenturyWithoutEnoughChangeoverTime() throws NotEnoughChangeoverTimeProvidedException {
        century.setMinChangeoverTime(14);
        centuryService.saveCentury(century);
        verify(centuryDAO, times(0)).save(century);
    }

    @Test
    public void testSaveCentury() throws NotEnoughChangeoverTimeProvidedException {
        century.setMinChangeoverTime(15);
        centuryService.saveCentury(century);
        verify(centuryDAO, times(1)).save(century);
    }

    @Test
    public void testListCenturys() {
        centuryService.listCenturies();
        verify(centuryDAO, times(1)).findAll();
    }

    @Test
    public void testLoadCentury() {
        centuryService.loadCentury(id);
        verify(centuryDAO, times(1)).findOne(id);
    }

    @Test
    public void testDeleteExistingCentury() {
        when(centuryDAO.findOne(id)).thenReturn(century);
        assertTrue(centuryService.deleteCentury(id));
        verify(centuryDAO, times(1)).delete(century);
    }

    @Test
    public void testFindByName() {
        centuryService.findCenturyByName(name);
        verify(centuryDAO, times(1)).findByName(name);
    }

    @After
    public void reset() {
        Mockito.reset(centuryDAO);
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public CenturyService centuryService() {
            return new CenturyServiceImpl(centuryDAO());
        }

        @Bean
        public CenturyDAO centuryDAO() {
            return mock(CenturyDAO.class);
        }
    }
}