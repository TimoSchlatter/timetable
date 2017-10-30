package service;


import de.nordakademie.iaa.dao.LectureDAO;
import de.nordakademie.iaa.model.Course;
import de.nordakademie.iaa.model.Lecture;
import de.nordakademie.iaa.service.LectureService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import de.nordakademie.iaa.service.impl.LectureServiceImpl;
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
public class LectureServiceTest {

    @Autowired
    private LectureService lectureService;

    @Autowired
    private LectureDAO lectureDAO;

    private final Course course = new Course('I', 154, "Kurs");
    private final Lecture lecture = new Lecture(course);

    @Test
    public void testSaveLecture() {
        lectureService.saveLecture(lecture);
        Mockito.verify(lectureDAO, times(1)).save(lecture);
    }

    @Test
    public void testListLectures() {
        lectureService.listLectures();
        Mockito.verify(lectureDAO, times(1)).findAll();
    }

    @Test
    public void testLoadLecture() {
        final Long id = 123L;
        lectureService.loadLecture(id);
        Mockito.verify(lectureDAO, times(1)).findOne(id);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteNonExistingLecture() throws EntityNotFoundException {
        final Long id = 123L;
        Mockito.when(lectureDAO.findOne(anyLong())).thenReturn(null);
        lectureService.deleteLecture(id);
    }

    @Test
    public void testDeleteExistingLecture() throws EntityNotFoundException {
        final Long id = 123L;
        Mockito.when(lectureDAO.findOne(anyLong())).thenReturn(lecture);
        lectureService.deleteLecture(id);
        Mockito.verify(lectureDAO, times(1)).findOne(id);
        Mockito.verify(lectureDAO, times(1)).delete(lecture);
    }

    @After
    public void clear() {
        Mockito.reset(lectureDAO);
    }

    @Configuration
    static class AccountServiceTestContextConfiguration {

        @Bean
        public LectureService lectureService() {
            return new LectureServiceImpl(lectureDAO());
        }

        @Bean
        public LectureDAO lectureDAO() {
            return Mockito.mock(LectureDAO.class);
        }
    }
}