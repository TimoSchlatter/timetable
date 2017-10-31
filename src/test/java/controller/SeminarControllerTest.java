package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.nordakademie.iaa.controller.SeminarController;
import de.nordakademie.iaa.model.Seminar;
import de.nordakademie.iaa.service.SeminarService;
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
public class SeminarControllerTest {

    @Autowired
    private SeminarController seminarController;

    @Autowired
    private SeminarService seminarService;

    private JacksonTester<Seminar> jacksonTester;
    private Seminar seminar = new Seminar(20, "Concurrency Java für BWLer", 42);
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(this.seminarController).build();
        seminar.setId(42L);
    }

    @Test
    public void testListSeminars() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
        List<Seminar> seminars = new ArrayList<>(Arrays.asList(seminar));
        when(this.seminarService.listSeminars()).thenReturn(seminars);
        MvcResult mvcResult = mockMvc.perform(get("/seminars"))
                .andExpect(status().isOk())
                .andReturn();
        verify(this.seminarService, times(1)).listSeminars();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<Seminar> seminarsResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<Seminar>>() {});
        assertEquals(seminars, seminarsResponse);
    }

    @Test
    public void testSaveSeminar() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc.perform(post("/seminars").content(jacksonTester.write(seminar).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(this.seminarService, times(1)).saveSeminar(seminar);
        doThrow(new RuntimeException()).when(seminarService).saveSeminar(any());
        mockMvc.perform(post("/seminars").content(jacksonTester.write(seminar).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(this.seminarService, times(2)).saveSeminar(seminar);
    }

    @Test
    public void testDeleteSeminar() throws Exception {
        mockMvc.perform(delete("/seminars/" + seminar.getId())).andExpect(status().isOk());
        verify(this.seminarService, times(1)).deleteSeminar(seminar.getId());
        doThrow(new EntityNotFoundException()).when(seminarService).deleteSeminar(anyLong());
        mockMvc.perform(delete("/seminars/" + seminar.getId())).andExpect(status().isNotFound());
        verify(this.seminarService, times(2)).deleteSeminar(seminar.getId());
    }

    @After
    public void reset() {
        Mockito.reset();
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public SeminarService seminarService() {
            return mock(SeminarService.class);
        }

        @Bean
        public SeminarController seminarController() {
            return new SeminarController(seminarService());
        }
    }
}