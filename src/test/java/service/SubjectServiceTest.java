package service;

import de.nordakademie.iaa.dao.SubjectDAO;
import de.nordakademie.iaa.model.Course;
import de.nordakademie.iaa.model.Module;
import de.nordakademie.iaa.model.Subject;
import de.nordakademie.iaa.model.SubjectType;
import de.nordakademie.iaa.service.SubjectService;
import de.nordakademie.iaa.service.impl.SubjectServiceImpl;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Test class for SubjectService class.
 *
 * @author Timo Schlatter
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SubjectServiceTest {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectDAO subjectDAO;

    private final Long id = 1L;
    private final Module module = new Course("Test-Modul", "Q", 121);
    private final SubjectType subjectType = SubjectType.LECTURE;
    private final Subject subject = new Subject(20, subjectType, module);

    @Test
    public void testSaveSubject() {
        subjectService.saveSubject(subject);
        Mockito.verify(subjectDAO, times(1)).save(subject);
    }

    @Test
    public void testListSubjects() {
        subjectService.listSubjects();
        Mockito.verify(subjectDAO, times(1)).findAll();
    }

    @Test
    public void testLoadSubject() {
        subjectService.loadSubject(id);
        Mockito.verify(subjectDAO, times(1)).findOne(id);
    }

    @Test
    public void testDeleteExistingSubject() {
        when(subjectDAO.findOne(id)).thenReturn(subject);
        assertTrue(subjectService.deleteSubject(id));
        verify(subjectDAO, times(1)).delete(subject);
    }

    @Test
    public void testFindSubjectBySubjectTypeAndModule() {
        subjectService.findSubjectBySubjectTypeAndModule(subjectType, module);
        verify(subjectDAO, times(1)).findBySubjectTypeAndModule(subjectType, module);
    }

    @Test
    public void testDeleteSubjectByModule() {
        subjectService.deleteSubjectByModule(module);
        verify(subjectDAO, times(1)).deleteByModule(module);
    }

    @After
    public void clear() {
        Mockito.reset(subjectDAO);
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public SubjectService subjectService() {
            return new SubjectServiceImpl(subjectDAO());
        }

        @Bean
        public SubjectDAO subjectDAO() {
            return Mockito.mock(SubjectDAO.class);
        }
    }
}