package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.nordakademie.iaa.controller.CohortController;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    private List<Cohort> cohorts = new ArrayList<>();
    private Cohort cohort = new Cohort("14", 30);
    private Maniple maniple = new Maniple("I", 30);
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(cohortController).build();
        cohort.setId(1L);
        maniple.setId(2L);
    }

    // TODO: Manipel und Zenturie zur Kohorte hinzufügen und alles testen
    @Test
    public void testListCohorts() throws Exception {
        cohorts.add(cohort);
        when(cohortService.listCohorts()).thenReturn(cohorts);
        mockMvc.perform(get("/cohorts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(toIntExact(cohort.getId()))))
                .andExpect(jsonPath("$.[0].minChangeoverTime", is(cohort.getMinChangeoverTime())))
                .andExpect(jsonPath("$.[0].name", is(cohort.getName())));
        verify(cohortService, times(1)).listCohorts();
    }

    @Test
    public void testSaveCohort() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc.perform(post("/cohorts").content(jacksonCohortTester.write(cohort).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.CREATED.value()));
        verify(cohortService, times(1)).saveCohort(cohort);
        doThrow(new RuntimeException()).when(cohortService).saveCohort(any());
        mockMvc.perform(post("/cohorts").content(jacksonCohortTester.write(cohort).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(cohortService, times(2)).saveCohort(cohort);
    }

    @Test
    public void testDeleteCohort() throws Exception {
        mockMvc.perform(delete("/cohorts/" + cohort.getId())).andExpect(status().isOk());
        verify(cohortService, times(1)).deleteCohort(cohort.getId());
        doThrow(new EntityNotFoundException()).when(cohortService).deleteCohort(anyLong());
        mockMvc.perform(delete("/cohorts/" + cohort.getId())).andExpect(status().isNotFound());
        verify(cohortService, times(2)).deleteCohort(cohort.getId());
    }

    @Test
    public void testAddManipleForExistingCohort() throws Exception {
        when(cohortService.loadCohort(cohort.getId())).thenReturn(cohort);
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc.perform(post("/cohorts/" + cohort.getId() + "/addManiple")
                .content(jacksonManipleTester.write(maniple).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.CREATED.value()));
        verify(cohortService, times(1)).loadCohort(cohort.getId());
        maniple.setName(maniple.getName() + cohort.getName());
        verify(manipleService, times(1)).saveManiple(maniple);
        assertThat(cohort.getManiples(), contains(maniple));
    }

    @Test
    public void testAddManipleForNonExistingCohort() throws Exception {
        final Long id = 1L;
        when(cohortService.loadCohort(anyLong())).thenReturn(null);
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc.perform(post("/cohorts/" + id + "/addManiple")
                .content(jacksonManipleTester.write(maniple).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(cohortService, times(1)).loadCohort(id);
        verify(manipleService, times(0)).saveManiple(any());
    }

    @After
    public void reset() {
        Mockito.reset(cohortService);
        Mockito.reset(manipleService);
    }

    @Configuration
    static class LoginControllerTestConfiguration {

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