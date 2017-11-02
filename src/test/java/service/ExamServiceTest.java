package service;


import de.nordakademie.iaa.dao.ExamDAO;
import de.nordakademie.iaa.model.Course;
import de.nordakademie.iaa.model.Exam;
import de.nordakademie.iaa.service.ExamService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import de.nordakademie.iaa.service.impl.ExamServiceImpl;
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
public class ExamServiceTest {

    @Autowired
    private ExamService examService;

    @Autowired
    private ExamDAO examDAO;

    private final Course course = new Course("I", 154, "Kurs");
    private final Exam exam = new Exam(course);

    @Test
    public void testSaveExam() {
        examService.saveExam(exam);
        Mockito.verify(examDAO, times(1)).save(exam);
    }

    @Test
    public void testListExams() {
        examService.listExams();
        Mockito.verify(examDAO, times(1)).findAll();
    }

    @Test
    public void testLoadExam() {
        final Long id = 123L;
        examService.loadExam(id);
        Mockito.verify(examDAO, times(1)).findOne(id);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteNonExistingExam() throws EntityNotFoundException {
        final Long id = 123L;
        Mockito.when(examDAO.findOne(anyLong())).thenReturn(null);
        examService.deleteExam(id);
    }

    @Test
    public void testDeleteExistingExam() throws EntityNotFoundException {
        final Long id = 123L;
        Mockito.when(examDAO.findOne(anyLong())).thenReturn(exam);
        examService.deleteExam(id);
        Mockito.verify(examDAO, times(1)).findOne(id);
        Mockito.verify(examDAO, times(1)).delete(exam);
    }

    @After
    public void clear() {
        Mockito.reset(examDAO);
    }

    @Configuration
    static class AccountServiceTestContextConfiguration {

        @Bean
        public ExamService examService() {
            return new ExamServiceImpl(examDAO());
        }

        @Bean
        public ExamDAO examDAO() {
            return Mockito.mock(ExamDAO.class);
        }
    }
}