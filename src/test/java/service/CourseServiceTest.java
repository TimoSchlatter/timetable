package service;


import de.nordakademie.iaa.dao.CourseDAO;
import de.nordakademie.iaa.model.Course;
import de.nordakademie.iaa.service.CourseService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import de.nordakademie.iaa.service.impl.CourseServiceImpl;
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
public class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseDAO courseDAO;

    private final String field = "I";
    private final int number = 101;
    private final String title = "Test Driven Development";
    private final String shortTitle = "TDD";
    private final Course course = new Course("I",123,"Nervige Tests");

    @Test
    public void testSaveCourse() {
        courseService.saveCourse(course);
        Mockito.verify(courseDAO, times(1)).save(course);
    }

    @Test
    public void testListCourses() {
        courseService.listCourses();
        Mockito.verify(courseDAO, times(1)).findAll();
    }

    @Test
    public void testLoadCourse() {
        final Long id = 123L;
        courseService.loadCourse(id);
        Mockito.verify(courseDAO, times(1)).findOne(id);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteNonExistingCourse() throws EntityNotFoundException {
        final Long id = 123L;
        Mockito.when(courseDAO.findOne(anyLong())).thenReturn(null);
        courseService.deleteCourse(id);
    }

    @Test
    public void testDeleteExistingCourse() throws EntityNotFoundException {
        final Long id = 123L;
        Mockito.when(courseDAO.findOne(anyLong())).thenReturn(course);
        courseService.deleteCourse(id);
        Mockito.verify(courseDAO, times(1)).findOne(id);
        Mockito.verify(courseDAO, times(1)).delete(course);
    }

    @After
    public void clear() {
        Mockito.reset(courseDAO);
    }

    @Configuration
    static class AccountServiceTestContextConfiguration {

        @Bean
        public CourseService courseService() {
            return new CourseServiceImpl(courseDAO());
        }

        @Bean
        public CourseDAO courseDAO() {
            return Mockito.mock(CourseDAO.class);
        }
    }
}
