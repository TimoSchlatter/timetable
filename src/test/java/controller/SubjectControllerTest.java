package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.nordakademie.iaa.controller.SubjectController;
import de.nordakademie.iaa.model.Seminar;
import de.nordakademie.iaa.model.SeminarType;
import de.nordakademie.iaa.model.Subject;
import de.nordakademie.iaa.model.SubjectType;
import de.nordakademie.iaa.service.SubjectService;
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
public class SubjectControllerTest {

    @Autowired
    private SubjectController subjectController;

    @Autowired
    private SubjectService subjectService;

    private JacksonTester<Subject> jacksonTester;
    private Seminar seminar = new Seminar("Test-Seminar", SeminarType.SONSTIGES);
    private Subject subject = new Subject(20, SubjectType.SEMINAR, seminar);
    private final Long subjectId = 1L;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(subjectController).build();
        subject.setId(subjectId);
        seminar.setId(subjectId+1);
    }

    @Test
    public void testListSubjects() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        JacksonTester.initFields(this, objectMapper);
        List<Subject> subjects = new ArrayList<>(Arrays.asList(subject));
        when(subjectService.listSubjects()).thenReturn(subjects);
        MvcResult mvcResult = mockMvc.perform(get("/subjects"))
                .andExpect(status().isOk())
                .andReturn();
        verify(subjectService, times(1)).listSubjects();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<Subject> subjectsResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<Subject>>(){});
        assertEquals(subjects, subjectsResponse);
    }

    @Test
    public void testSaveSubject() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping applicability, JsonTypeInfo.As includeAs)
        objectMapper.enableDefaultTyping();
        JacksonTester.initFields(this, objectMapper);
        // Subject already existing
        when(subjectService.loadSubject(subjectId)).thenReturn(subject);
        mockMvc.perform(post("/subjects").content(jacksonTester.write(subject).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(subjectService, times(0)).saveSubject(subject);
        // Subject not yet existing
        when(subjectService.loadSubject(subjectId)).thenReturn(null);
        mockMvc.perform(post("/subjects").content(jacksonTester.write(subject).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.CREATED.value()));
        verify(subjectService, times(1)).saveSubject(subject);
        // Subject not yet existing & saving failed
        doThrow(new RuntimeException()).when(subjectService).saveSubject(any());
        mockMvc.perform(post("/subjects").content(jacksonTester.write(subject).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(subjectService, times(2)).saveSubject(subject);
    }

    @Test
    public void testUpdateSubject() throws Exception {
        final String url = "/subjects/" + subjectId;
        JacksonTester.initFields(this, new ObjectMapper());
        // Subject not existing
        when(subjectService.loadSubject(subjectId)).thenReturn(null);
        mockMvc.perform(put(url).content(jacksonTester.write(subject).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(subjectService, times(0)).saveSubject(subject);
        // Subject existing
        when(subjectService.loadSubject(subjectId)).thenReturn(subject);
        mockMvc.perform(put(url).content(jacksonTester.write(subject).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(subjectService, times(1)).saveSubject(subject);
        // Subject existing & updating failed
        doThrow(new RuntimeException()).when(subjectService).saveSubject(any());
        mockMvc.perform(put(url).content(jacksonTester.write(subject).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(subjectService, times(2)).saveSubject(subject);
    }

    @Test
    public void testDeleteSubject() throws Exception {
        mockMvc.perform(delete("/subjects/" + subject.getId())).andExpect(status().isOk());
        verify(subjectService, times(1)).deleteSubject(subject.getId());
        doThrow(new EntityNotFoundException()).when(subjectService).deleteSubject(anyLong());
        mockMvc.perform(delete("/subjects/" + subject.getId())).andExpect(status().isNotFound());
        verify(subjectService, times(2)).deleteSubject(subject.getId());
    }

    @After
    public void reset() {
        Mockito.reset(subjectService);
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public SubjectService subjectService() {
            return mock(SubjectService.class);
        }

        @Bean
        public SubjectController subjectController() {
            return new SubjectController(subjectService());
        }
    }
}