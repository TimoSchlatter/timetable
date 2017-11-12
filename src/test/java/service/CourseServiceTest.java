package service;

import de.nordakademie.iaa.dao.CourseDAO;
import de.nordakademie.iaa.model.Course;
import de.nordakademie.iaa.service.CourseService;
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

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Test class for CourseService class.
 *
 * @author Timo Schlatter
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseDAO courseDAO;

    private final String field = "I";
    private final int number = 101;
    private final Long id = 1L;
    private final String title = "Test Driven Development";
    private final String shortTitle = "TDD";
    private final Course course = new Course(title, shortTitle, field, number);

    @Test
    public void testSaveCourse() {
        courseService.saveCourse(course);
        verify(courseDAO, times(1)).save(course);
    }

    @Test
    public void testListCourses() {
        courseService.listCourses();
        verify(courseDAO, times(1)).findAll();
    }

    @Test
    public void testLoadCourse() {
        courseService.loadCourse(id);
        verify(courseDAO, times(1)).findOne(id);
    }

    @Test
    public void testDeleteExistingCourse() {
        when(courseDAO.findOne(id)).thenReturn(course);
        assertTrue(courseService.deleteCourse(id));
        verify(courseDAO, times(1)).delete(course);
    }

    @Test
    public void testFindByTitle() {
        courseService.findCourseByTitle(title);
        verify(courseDAO, times(1)).findByTitle(title);
    }

    @After
    public void clear() {
        Mockito.reset(courseDAO);
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public CourseService courseService() {
            return new CourseServiceImpl(courseDAO());
        }

        @Bean
        public CourseDAO courseDAO() {
            return mock(CourseDAO.class);
        }
    }
}