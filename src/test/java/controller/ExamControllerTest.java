package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.nordakademie.iaa.controller.ExamController;
import de.nordakademie.iaa.model.Course;
import de.nordakademie.iaa.model.Exam;
import de.nordakademie.iaa.service.ExamService;
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
public class ExamControllerTest {

    @Autowired
    private ExamController examController;

    @Autowired
    private ExamService examService;

    private JacksonTester<Exam> jacksonTester;
    private Course course = new Course("I", 154, "Modul");
    private Exam exam = new Exam(20, course);
    private final Long examId = 1L;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(examController).build();
        exam.setId(1L);
        course.setId(examId+1);
    }

    @Test
    public void testListExams() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
        List<Exam> exams = new ArrayList<>(Arrays.asList(exam));
        when(examService.listExams()).thenReturn(exams);
        MvcResult mvcResult = mockMvc.perform(get("/exams"))
                .andExpect(status().isOk())
                .andReturn();
        verify(examService, times(1)).listExams();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<Exam> examsResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<Exam>>() {});
        assertEquals(exams, examsResponse);
    }

    @Test
    public void testSaveExam() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc.perform(post("/exams").content(jacksonTester.write(exam).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(examService, times(1)).saveExam(exam);
        doThrow(new RuntimeException()).when(examService).saveExam(any());
        mockMvc.perform(post("/exams").content(jacksonTester.write(exam).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(examService, times(2)).saveExam(exam);
    }

    @Test
    public void testUpdateExam() throws Exception {
        final String url = "/exams/" + examId;
        JacksonTester.initFields(this, new ObjectMapper());
        when(examService.loadExam(examId)).thenReturn(exam);
        mockMvc.perform(put(url).content(jacksonTester.write(exam).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(examService, times(1)).saveExam(exam);

        doThrow(new RuntimeException()).when(examService).saveExam(any());
        mockMvc.perform(put(url).content(jacksonTester.write(exam).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(examService, times(2)).saveExam(exam);

        when(examService.loadExam(examId)).thenReturn(null);
        mockMvc.perform(put(url).content(jacksonTester.write(exam).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(examService, times(2)).saveExam(exam);
    }
    
    @Test
    public void testDeleteExam() throws Exception {
        mockMvc.perform(delete("/exams/" + examId)).andExpect(status().isOk());
        verify(examService, times(1)).deleteExam(examId);
        doThrow(new EntityNotFoundException()).when(examService).deleteExam(anyLong());
        mockMvc.perform(delete("/exams/" + examId)).andExpect(status().isNotFound());
        verify(examService, times(2)).deleteExam(examId);
    }

    @After
    public void reset() {
        Mockito.reset(examService);
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public ExamService examService() {
            return mock(ExamService.class);
        }

        @Bean
        public ExamController examController() {
            return new ExamController(examService());
        }
    }
}