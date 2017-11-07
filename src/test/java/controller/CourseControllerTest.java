package controller;

import com.fasterxml.jackson.core.type.TypeReference;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CourseControllerTest {

    @Autowired
    private CourseController courseController;

    @Autowired
    private CourseService courseService;

    private MockMvc mockMvc;
    private JacksonTester<Course> jacksonTester;
    private final Course course = new Course("Test Driven Development", "X", 232);
    private final Long courseId = 1L;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
        course.setId(courseId);
    }

    @Test
    public void testListCourses() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
        List<Course> courses = new ArrayList<>(Arrays.asList(course));
        when(courseService.listCourses()).thenReturn(courses);
        MvcResult mvcResult = mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andReturn();
        verify(courseService, times(1)).listCourses();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<Course> coursesResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<Course>>() {});
        assertEquals(courses, coursesResponse);
    }

    @Test
    public void testSaveCourse() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
        String url = "/courses";
        // Course already existing
        when(courseService.findByTitle(course.getTitle())).thenReturn(course);
        mockMvc.perform(post(url).content(jacksonTester.write(course).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(courseService, times(0)).saveCourse(course);
        // Course not yet existing
        when(courseService.findByTitle(course.getTitle())).thenReturn(null);
        mockMvc.perform(post(url).content(jacksonTester.write(course).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.CREATED.value()));
        verify(courseService, times(1)).saveCourse(course);
        // Course not yet existing & saving failed
        doThrow(new RuntimeException()).when(courseService).saveCourse(any());
        mockMvc.perform(post(url).content(jacksonTester.write(course).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(courseService, times(2)).saveCourse(course);
    }

    @Test
    public void testUpdateCourse() throws Exception {
        final String url = "/courses/" + courseId;
        JacksonTester.initFields(this, new ObjectMapper());
        // Course not existing
        when(courseService.loadCourse(courseId)).thenReturn(null);
        mockMvc.perform(put(url).content(jacksonTester.write(course).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(courseService, times(0)).saveCourse(course);
        // Course existing
        when(courseService.loadCourse(courseId)).thenReturn(course);
        mockMvc.perform(put(url).content(jacksonTester.write(course).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(courseService, times(1)).saveCourse(course);
        // Course existing & updating failed
        doThrow(new RuntimeException()).when(courseService).saveCourse(any());
        mockMvc.perform(put(url).content(jacksonTester.write(course).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(courseService, times(2)).saveCourse(course);
    }

    @Test
    public void testDeleteCourse() throws Exception {
        mockMvc.perform(delete("/courses/" + course.getId())).andExpect(status().isOk());
        verify(courseService, times(1)).deleteCourse(course.getId());
        doThrow(new EntityNotFoundException()).when(courseService).deleteCourse(anyLong());
        mockMvc.perform(delete("/courses/" + course.getId())).andExpect(status().isBadRequest());
        verify(courseService, times(2)).deleteCourse(course.getId());
    }

    @After
    public void reset() {
        Mockito.reset(courseService);
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