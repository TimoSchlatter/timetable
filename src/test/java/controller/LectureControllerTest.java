package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.nordakademie.iaa.controller.LectureController;
import de.nordakademie.iaa.model.Course;
import de.nordakademie.iaa.model.Lecture;
import de.nordakademie.iaa.service.LectureService;
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
public class LectureControllerTest {

    @Autowired
    private LectureController lectureController;

    @Autowired
    private LectureService lectureService;

    private JacksonTester<Lecture> jacksonTester;
    private Course course = new Course('I', 154, "Modul");
    private Lecture lecture = new Lecture(20, course);
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(this.lectureController).build();
        lecture.setId(1L);
        course.setId(2L);
    }

    @Test
    public void testListLectures() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
        List<Lecture> lectures = new ArrayList<>(Arrays.asList(lecture));
        when(this.lectureService.listLectures()).thenReturn(lectures);
        MvcResult mvcResult = mockMvc.perform(get("/lectures"))
                .andExpect(status().isOk())
                .andReturn();
        verify(this.lectureService, times(1)).listLectures();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<Lecture> lecturesResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<Lecture>>() {});
        assertEquals(lectures, lecturesResponse);
    }

    @Test
    public void testSaveLecture() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc.perform(post("/lectures").content(jacksonTester.write(lecture).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(this.lectureService, times(1)).saveLecture(lecture);
        doThrow(new RuntimeException()).when(lectureService).saveLecture(any());
        mockMvc.perform(post("/lectures").content(jacksonTester.write(lecture).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(this.lectureService, times(2)).saveLecture(lecture);
    }

    @Test
    public void testDeleteLecture() throws Exception {
        mockMvc.perform(delete("/lectures/" + lecture.getId())).andExpect(status().isOk());
        verify(this.lectureService, times(1)).deleteLecture(lecture.getId());
        doThrow(new EntityNotFoundException()).when(lectureService).deleteLecture(anyLong());
        mockMvc.perform(delete("/lectures/" + lecture.getId())).andExpect(status().isNotFound());
        verify(this.lectureService, times(2)).deleteLecture(lecture.getId());
    }

    @After
    public void reset() {
        Mockito.reset();
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public LectureService lectureService() {
            return mock(LectureService.class);
        }

        @Bean
        public LectureController lectureController() {
            return new LectureController(lectureService());
        }
    }
}