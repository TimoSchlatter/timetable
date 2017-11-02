package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.nordakademie.iaa.controller.ManipleController;
import de.nordakademie.iaa.model.Century;
import de.nordakademie.iaa.model.Maniple;
import de.nordakademie.iaa.service.CenturyService;
import de.nordakademie.iaa.service.ManipleService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ManipleControllerTest {

    @Autowired
    private ManipleController manipleController;

    @Autowired
    private ManipleService manipleService;

    @Autowired
    private CenturyService centuryService;

    private JacksonTester<Maniple> jacksonManipleTester;
    private JacksonTester<Century> jacksonCenturyTester;
    private Maniple maniple = new Maniple("I14", 30);
    private Century century = new Century("a", 30);
    private final Long manipleId = 1L;
    private final Long centuryId = manipleId+1;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(manipleController).build();
        maniple.setId(manipleId);
        century.setId(centuryId);
    }

    @Test
    public void testListManiples() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
        maniple.addCentury(century);
        List<Maniple> maniples = new ArrayList<>(Arrays.asList(maniple));
        when(this.manipleService.listManiples()).thenReturn(maniples);
        MvcResult mvcResult = mockMvc.perform(get("/maniples"))
                .andExpect(status().isOk())
                .andReturn();
        verify(this.manipleService, times(1)).listManiples();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<Maniple> maniplesResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<Maniple>>() {});
        assertEquals(maniples, maniplesResponse);
    }

    @Test
    public void testAddCenturyForExistingManiple() throws Exception {
        when(manipleService.loadManiple(maniple.getId())).thenReturn(maniple);
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc.perform(post("/maniples/" + maniple.getId() + "/addCentury")
                .content(jacksonCenturyTester.write(century).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.CREATED.value()));
        verify(manipleService, times(1)).loadManiple(maniple.getId());
        century.setName(maniple.getName() + century.getName());
        verify(centuryService, times(1)).saveCentury(century);
        assertThat(maniple.getCenturies(), contains(century));
    }

    @Test
    public void testAddCenturyForNonExistingManiple() throws Exception {
        final Long id = 1L;
        when(manipleService.loadManiple(anyLong())).thenReturn(null);
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc.perform(post("/maniples/" + id + "/addCentury")
                .content(jacksonCenturyTester.write(century).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(manipleService, times(1)).loadManiple(id);
        verify(centuryService, times(0)).saveCentury(any());
    }

    @Test
    public void testRemoveManipleFromCohort() throws Exception {
        when(manipleService.loadManiple(manipleId)).thenReturn(maniple);
        when(centuryService.loadCentury(centuryId)).thenReturn(century);
        mockMvc.perform(delete("/maniples/" + manipleId + "/deleteCentury/" + centuryId)).andExpect(status().isOk());
        verify(manipleService, times(1)).loadManiple(manipleId);
        verify(centuryService, times(1)).loadCentury(centuryId);
        verify(centuryService, times(1)).deleteCentury(any());
    }

    @Test
    public void testRemoveManipleFromNonExistingCohort() throws Exception {
        when(manipleService.loadManiple(anyLong())).thenReturn(null);
        when(centuryService.loadCentury(centuryId)).thenReturn(century);
        mockMvc.perform(delete("/maniples/" + manipleId + "/deleteCentury/" + centuryId)).andExpect(status().isNotFound());
        verify(manipleService, times(1)).loadManiple(manipleId);
        verify(centuryService, times(1)).loadCentury(centuryId);
        verify(centuryService, times(0)).deleteCentury(any());
    }

    @Test
    public void testRemoveNonExistingManipleFromCohort() throws Exception {
        when(manipleService.loadManiple(manipleId)).thenReturn(maniple);
        when(centuryService.loadCentury(centuryId)).thenReturn(null);
        mockMvc.perform(delete("/maniples/" + manipleId + "/deleteCentury/" + centuryId)).andExpect(status().isNotFound());
        verify(manipleService, times(1)).loadManiple(manipleId);
        verify(centuryService, times(1)).loadCentury(centuryId);
        verify(centuryService, times(0)).deleteCentury(any());
    }

    @Test
    public void testRemoveNonExistingManipleFromNonExistingCohort() throws Exception {
        when(manipleService.loadManiple(manipleId)).thenReturn(null);
        when(centuryService.loadCentury(centuryId)).thenReturn(null);
        mockMvc.perform(delete("/maniples/" + manipleId + "/deleteCentury/" + centuryId)).andExpect(status().isNotFound());
        verify(manipleService, times(1)).loadManiple(manipleId);
        verify(centuryService, times(1)).loadCentury(centuryId);
        verify(centuryService, times(0)).deleteCentury(any());
    }
    
    @After
    public void reset() {
        Mockito.reset(manipleService);
        Mockito.reset(centuryService);
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public ManipleService manipleService() {
            return mock(ManipleService.class);
        }

        @Bean
        public CenturyService centuryService() {
            return mock(CenturyService.class);
        }

        @Bean
        public ManipleController manipleController() {
            return new ManipleController(manipleService(), centuryService());
        }
    }
}