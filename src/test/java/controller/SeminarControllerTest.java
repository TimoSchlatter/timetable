package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.nordakademie.iaa.controller.SeminarController;
import de.nordakademie.iaa.model.Seminar;
import de.nordakademie.iaa.model.SeminarType;
import de.nordakademie.iaa.service.SeminarService;
import de.nordakademie.iaa.service.SubjectService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
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
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SeminarControllerTest {

    @Autowired
    private SeminarController seminarController;

    @Autowired
    private SeminarService seminarService;

    @Autowired
    private SubjectService subjectService;

    private MockMvc mockMvc;
    private JacksonTester<Seminar> jacksonTester;
    private final String title = "Test-Seminar";
    private final Seminar seminar = new Seminar(title, SeminarType.OTHER);
    private final Long seminarId = 1L;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(seminarController).build();
        seminar.setId(seminarId);
    }

    @Test
    public void testListSeminars() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
        List<Seminar> seminars = new ArrayList<>(Arrays.asList(seminar));
        when(seminarService.listSeminars()).thenReturn(seminars);
        MvcResult mvcResult = mockMvc.perform(get("/seminars"))
                .andExpect(status().isOk())
                .andReturn();
        verify(seminarService, times(1)).listSeminars();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<Seminar> seminarsResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<Seminar>>() {});
        assertEquals(seminars, seminarsResponse);
    }

    @Test
    public void testSaveSeminar() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
        String url = "/seminars";
        // Seminar already existing
        when(seminarService.findSeminarByTitle(title)).thenReturn(seminar);
        mockMvc.perform(post(url).content(jacksonTester.write(seminar).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(seminarService, times(0)).saveSeminar(seminar);
        // Seminar not yet existing
        when(seminarService.findSeminarByTitle(title)).thenReturn(null);
        mockMvc.perform(post(url).content(jacksonTester.write(seminar).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.CREATED.value()));
        verify(seminarService, times(1)).saveSeminar(seminar);
        // Seminar not yet existing & saving failed
        doThrow(new RuntimeException()).when(seminarService).saveSeminar(any());
        mockMvc.perform(post(url).content(jacksonTester.write(seminar).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(seminarService, times(2)).saveSeminar(seminar);
    }

    @Test
    public void testUpdateSeminar() throws Exception {
        final String url = "/seminars/" + seminarId;
        JacksonTester.initFields(this, new ObjectMapper());
        // Seminar not existing
        when(seminarService.loadSeminar(seminarId)).thenReturn(null);
        mockMvc.perform(put(url).content(jacksonTester.write(seminar).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(seminarService, times(0)).saveSeminar(seminar);
        // Seminar existing
        when(seminarService.loadSeminar(seminarId)).thenReturn(seminar);
        mockMvc.perform(put(url).content(jacksonTester.write(seminar).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(seminarService, times(1)).saveSeminar(seminar);
        // Seminar existing & updating failed
        doThrow(new RuntimeException()).when(seminarService).saveSeminar(any());
        mockMvc.perform(put(url).content(jacksonTester.write(seminar).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(seminarService, times(2)).saveSeminar(seminar);
    }

    @Test
    public void testDeleteSeminar() throws Exception {
        final String url = "/seminars/" + seminarId;
        InOrder inOrder = inOrder(seminarService, subjectService);
        // Seminar not existing
        when(seminarService.loadSeminar(seminarId)).thenReturn(null);
        mockMvc.perform(delete(url)).andExpect(status().isBadRequest());
        inOrder.verify(seminarService, times(1)).loadSeminar(seminarId);
        inOrder.verify(subjectService, times(0)).deleteSubjectByModule(seminar);
        inOrder.verify(seminarService, times(0)).deleteSeminar(seminarId);
        // Seminar existing
        when(seminarService.loadSeminar(seminarId)).thenReturn(seminar);
        mockMvc.perform(delete(url)).andExpect(status().isOk());
        inOrder.verify(seminarService, times(1)).loadSeminar(seminarId);
        inOrder.verify(subjectService, times(1)).deleteSubjectByModule(seminar);
        inOrder.verify(seminarService, times(1)).deleteSeminar(seminarId);
    }

    @After
    public void reset() {
        Mockito.reset(seminarService);
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public SeminarService seminarService() {
            return mock(SeminarService.class);
        }

        @Bean
        SubjectService subjectService() {
            return mock(SubjectService.class);
        }

        @Bean
        public SeminarController seminarController() {
            return new SeminarController(seminarService(), subjectService());
        }
    }
}