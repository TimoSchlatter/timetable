package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.nordakademie.iaa.controller.CohortController;
import de.nordakademie.iaa.model.Century;
import de.nordakademie.iaa.model.Cohort;
import de.nordakademie.iaa.model.Maniple;
import de.nordakademie.iaa.service.CohortService;
import de.nordakademie.iaa.service.ManipleService;
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

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CohortControllerTest {

    @Autowired
    private CohortController cohortController;

    @Autowired
    private CohortService cohortService;

    @Autowired
    private ManipleService manipleService;

    private JacksonTester<Cohort> jacksonCohortTester;
    private JacksonTester<Maniple> jacksonManipleTester;
    private Cohort cohort = new Cohort("14", 30);
    private Maniple maniple = new Maniple("I", 30);
    private Century century = new Century("a", 30);
    private final Long cohortId = 1L;
    private final Long manipleId = cohortId+1;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(cohortController).build();
        cohort.setId(cohortId);
        maniple.setId(manipleId);
        century.setId(manipleId+1);
    }

    @Test
    public void testListCohorts() throws Exception {
        maniple.addCentury(century);
        cohort.addManiple(maniple);
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
        List<Cohort> cohorts = new ArrayList<>(Arrays.asList(cohort));
        when(cohortService.listCohorts()).thenReturn(cohorts);
        MvcResult mvcResult = mockMvc.perform(get("/cohorts"))
                .andExpect(status().isOk())
                .andReturn();
        verify(cohortService, times(1)).listCohorts();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<Cohort> cohortsResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<Cohort>>() {});
        assertEquals(cohorts, cohortsResponse);
    }

    @Test
    public void testSaveCohort() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
        String url = "/cohorts";
        // Cohort already existing
        when(cohortService.findByName(cohort.getName())).thenReturn(cohort);
        mockMvc.perform(post(url).content(jacksonCohortTester.write(cohort).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(cohortService, times(0)).saveCohort(cohort);
        // Cohort not yet existing
        when(cohortService.findByName(cohort.getName())).thenReturn(null);
        mockMvc.perform(post(url).content(jacksonCohortTester.write(cohort).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.CREATED.value()));
        verify(cohortService, times(1)).saveCohort(cohort);
        // Cohort not yet existing & saving failed
        doThrow(new RuntimeException()).when(cohortService).saveCohort(any());
        mockMvc.perform(post(url).content(jacksonCohortTester.write(cohort).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(cohortService, times(2)).saveCohort(cohort);
    }

    @Test
    public void testUpdateCohort() throws Exception {
        final String url = "/cohorts/" + cohortId;
        JacksonTester.initFields(this, new ObjectMapper());
        // Cohort not existing
        when(cohortService.loadCohort(cohortId)).thenReturn(null);
        mockMvc.perform(put(url).content(jacksonCohortTester.write(cohort).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(cohortService, times(0)).saveCohort(cohort);
        // Cohort existing
        when(cohortService.loadCohort(cohortId)).thenReturn(cohort);
        mockMvc.perform(put(url).content(jacksonCohortTester.write(cohort).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(cohortService, times(1)).saveCohort(cohort);
        // Cohort existing & updating failed
        doThrow(new RuntimeException()).when(cohortService).saveCohort(any());
        mockMvc.perform(put(url).content(jacksonCohortTester.write(cohort).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(cohortService, times(2)).saveCohort(cohort);
    }

    @Test
    public void testDeleteCohort() throws Exception {
        mockMvc.perform(delete("/cohorts/" + cohortId)).andExpect(status().isOk());
        verify(cohortService, times(1)).deleteCohort(cohortId);
        doThrow(new EntityNotFoundException()).when(cohortService).deleteCohort(anyLong());
        mockMvc.perform(delete("/cohorts/" + cohortId)).andExpect(status().isNotFound());
        verify(cohortService, times(2)).deleteCohort(cohortId);
    }

    @Test
    public void testAddManipleForExistingCohort() throws Exception {
        when(cohortService.loadCohort(cohortId)).thenReturn(cohort);
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc.perform(post("/cohorts/" + cohortId + "/addManiple")
                .content(jacksonManipleTester.write(maniple).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.CREATED.value()));
        verify(cohortService, times(1)).loadCohort(cohortId);
        maniple.setName(maniple.getName() + cohort.getName());
        verify(manipleService, times(1)).saveManiple(maniple);
        assertThat(cohort.getManiples(), contains(maniple));
    }

    @Test
    public void testAddManipleForNonExistingCohort() throws Exception {
        when(cohortService.loadCohort(anyLong())).thenReturn(null);
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc.perform(post("/cohorts/" + cohortId + "/addManiple")
                .content(jacksonManipleTester.write(maniple).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(cohortService, times(1)).loadCohort(cohortId);
        verify(manipleService, times(0)).saveManiple(any());
    }

    @Test
    public void testRemoveManipleFromCohort() throws Exception {
        when(cohortService.loadCohort(cohortId)).thenReturn(cohort);
        when(manipleService.loadManiple(manipleId)).thenReturn(maniple);
        mockMvc.perform(delete("/cohorts/" + cohortId + "/deleteManiple/" + manipleId)).andExpect(status().isOk());
        verify(cohortService, times(1)).loadCohort(cohortId);
        verify(manipleService, times(1)).loadManiple(manipleId);
        verify(manipleService, times(1)).deleteManiple(any());
    }

    @Test
    public void testRemoveManipleFromNonExistingCohort() throws Exception {
        when(cohortService.loadCohort(anyLong())).thenReturn(null);
        when(manipleService.loadManiple(manipleId)).thenReturn(maniple);
        mockMvc.perform(delete("/cohorts/" + cohortId + "/deleteManiple/" + manipleId)).andExpect(status().isNotFound());
        verify(cohortService, times(1)).loadCohort(cohortId);
        verify(manipleService, times(1)).loadManiple(manipleId);
        verify(manipleService, times(0)).deleteManiple(any());
    }

    @Test
    public void testRemoveNonExistingManipleFromCohort() throws Exception {
        when(cohortService.loadCohort(cohortId)).thenReturn(cohort);
        when(manipleService.loadManiple(manipleId)).thenReturn(null);
        mockMvc.perform(delete("/cohorts/" + cohortId + "/deleteManiple/" + manipleId)).andExpect(status().isNotFound());
        verify(cohortService, times(1)).loadCohort(cohortId);
        verify(manipleService, times(1)).loadManiple(manipleId);
        verify(manipleService, times(0)).deleteManiple(any());
    }

    @Test
    public void testRemoveNonExistingManipleFromNonExistingCohort() throws Exception {
        when(cohortService.loadCohort(cohortId)).thenReturn(null);
        when(manipleService.loadManiple(manipleId)).thenReturn(null);
        mockMvc.perform(delete("/cohorts/" + cohortId + "/deleteManiple/" + manipleId)).andExpect(status().isNotFound());
        verify(cohortService, times(1)).loadCohort(cohortId);
        verify(manipleService, times(1)).loadManiple(manipleId);
        verify(manipleService, times(0)).deleteManiple(any());
    }

    @After
    public void reset() {
        Mockito.reset(cohortService);
        Mockito.reset(manipleService);
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public CohortService cohortService() {
            return mock(CohortService.class);
        }

        @Bean
        public ManipleService manipleService() {
            return mock(ManipleService.class);
        }

        @Bean
        public CohortController cohortController() {
            return new CohortController(cohortService(), manipleService());
        }
    }
}