package service;


import de.nordakademie.iaa.dao.CenturyDAO;
import de.nordakademie.iaa.model.Century;
import de.nordakademie.iaa.service.CenturyService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
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

import static org.mockito.Matchers.anyLong;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CenturyServiceTest {

    @Autowired
    private CenturyService centuryService;

    @Autowired
    private CenturyDAO centuryDAO;

    private final String name = "A";
    private final int numberOfStudents = 42;
    private final Century century = new Century(name, numberOfStudents);

    @Test
    public void testSaveCentury() {
        centuryService.saveCentury(century);
        Mockito.verify(centuryDAO, times(1)).save(century);
    }

    @Test
    public void testListCenturys() {
        centuryService.listCenturies();
        Mockito.verify(centuryDAO, times(1)).findAll();
    }

    @Test
    public void testLoadCentury() {
        final Long id = 123L;
        centuryService.loadCentury(id);
        Mockito.verify(centuryDAO, times(1)).findOne(id);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteNonExistingCentury() throws EntityNotFoundException {
        final Long id = 123L;
        Mockito.when(centuryDAO.findOne(anyLong())).thenReturn(null);
        centuryService.deleteCentury(id);
    }

    @Test
    public void testDeleteExistingCentury() throws EntityNotFoundException {
        final Long id = 123L;
        Mockito.when(centuryDAO.findOne(anyLong())).thenReturn(century);
        centuryService.deleteCentury(id);
        Mockito.verify(centuryDAO, times(1)).findOne(id);
        Mockito.verify(centuryDAO, times(1)).delete(century);
    }

    @After
    public void clear() {
        Mockito.reset(centuryDAO);
    }

    @Configuration
    static class AccountServiceTestContextConfiguration {

        @Bean
        public CenturyService centuryService() {
            return new CenturyServiceImpl(centuryDAO());
        }

        @Bean
        public CenturyDAO centuryDAO() {
            return Mockito.mock(CenturyDAO.class);
        }
    }
}
