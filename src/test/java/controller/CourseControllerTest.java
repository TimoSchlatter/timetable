package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.nordakademie.iaa.controller.CourseController;
import de.nordakademie.iaa.model.Course;
import de.nordakademie.iaa.service.CourseService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static java.lang.Math.toIntExact;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CourseControllerTest {

    @Autowired
    private CourseController courseController;

    @Autowired
    private CourseService courseService;

    private JacksonTester<Course> jacksonTester;
    private Course course = new Course('X', 232, "Test Driven Development");
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(this.courseController).build();
        course.setId(42L);
    }

    @Test
    public void testListCourses() throws Exception {
        when(this.courseService.listCourses()).thenReturn(Arrays.asList(course));
        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(toIntExact(course.getId()))))
                .andExpect(jsonPath("$.[0].field", is(((Character.toString(course.getField()))))))
                .andExpect(jsonPath("$.[0].number", is(course.getNumber())))
                .andExpect(jsonPath("$.[0].title", is(course.getTitle())));
        verify(this.courseService, times(1)).listCourses();
    }

    @Test
    public void testSaveCourse() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc.perform(post("/courses").content(jacksonTester.write(course).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(this.courseService, times(1)).saveCourse(course);
        doThrow(new RuntimeException()).when(courseService).saveCourse(any());
        mockMvc.perform(post("/courses").content(jacksonTester.write(course).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(this.courseService, times(2)).saveCourse(course);
    }

    @Test
    public void testDeleteCourse() throws Exception {
        mockMvc.perform(delete("/courses/" + course.getId())).andExpect(status().isOk());
        verify(this.courseService, times(1)).deleteCourse(course.getId());
        doThrow(new EntityNotFoundException()).when(courseService).deleteCourse(anyLong());
        mockMvc.perform(delete("/courses/" + course.getId())).andExpect(status().isNotFound());
        verify(this.courseService, times(2)).deleteCourse(course.getId());
    }

    @After
    public void reset() {
        Mockito.reset();
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public CourseService courseService() {
            return mock(CourseService.class);
        }

        @Bean
        public CourseController courseController() {
            return new CourseController(courseService());
        }
    }
}