package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.nordakademie.iaa.controller.ManipleController;
import de.nordakademie.iaa.model.Century;
import de.nordakademie.iaa.model.Maniple;
import de.nordakademie.iaa.service.CenturyService;
import de.nordakademie.iaa.service.EventService;
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
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for ManipleController class.
 *
 * @author Timo Schlatter
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ManipleControllerTest {

    @Autowired
    private ManipleController manipleController;

    @Autowired
    private EventService eventService;

    @Autowired
    private ManipleService manipleService;

    @Autowired
    private CenturyService centuryService;

    private MockMvc mockMvc;
    private JacksonTester<Maniple> jacksonManipleTester;
    private JacksonTester<Century> jacksonCenturyTester;
    private final Maniple maniple = new Maniple("I14", 30);
    private final Century century = new Century("a", 30);
    private final String newCenturyName = maniple.getName() + century.getName();
    private final Long manipleId = 1L;
    private final Long centuryId = manipleId+1;

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
    public void testUpdateManiple() throws Exception {
        final String url = "/maniples/" + manipleId;
        JacksonTester.initFields(this, new ObjectMapper());
        // Maniple not existing
        when(manipleService.loadManiple(manipleId)).thenReturn(null);
        mockMvc.perform(put(url).content(jacksonManipleTester.write(maniple).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(manipleService, times(0)).saveManiple(maniple);
        // Maniple existing
        when(manipleService.loadManiple(manipleId)).thenReturn(maniple);
        mockMvc.perform(put(url).content(jacksonManipleTester.write(maniple).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(manipleService, times(1)).saveManiple(maniple);
        // Maniple existing & updating failed
        doThrow(new RuntimeException()).when(manipleService).saveManiple(any());
        mockMvc.perform(put(url).content(jacksonManipleTester.write(maniple).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(manipleService, times(2)).saveManiple(maniple);
    }

    @Test
    public void testAddNonExistingCenturyToExistingManiple() throws Exception {
        when(manipleService.loadManiple(manipleId)).thenReturn(maniple);
        when(centuryService.findCenturyByName(newCenturyName)).thenReturn(null);
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc.perform(post("/maniples/" + manipleId + "/addCentury")
                .content(jacksonCenturyTester.write(century).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.CREATED.value()));
        verify(manipleService, times(1)).loadManiple(manipleId);
        verify(centuryService, times(1)).findCenturyByName(newCenturyName);
        century.setName(newCenturyName);
        verify(centuryService, times(1)).saveCentury(century);
        assertThat(maniple.getCenturies(), contains(century));
    }

    @Test
    public void testAddExistingCenturyToExistingManiple() throws Exception {
        when(manipleService.loadManiple(manipleId)).thenReturn(maniple);
        when(centuryService.findCenturyByName(newCenturyName)).thenReturn(century);
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc.perform(post("/maniples/" + manipleId + "/addCentury")
                .content(jacksonCenturyTester.write(century).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(manipleService, times(1)).loadManiple(manipleId);
        verify(centuryService, times(1)).findCenturyByName(newCenturyName);
        verify(centuryService, times(0)).saveCentury(any());
        assertThat(century, not(isIn(maniple.getCenturies())));
    }

    @Test
    public void testAddCenturyToNonExistingManiple() throws Exception {
        when(manipleService.loadManiple(anyLong())).thenReturn(null);
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc.perform(post("/maniples/" + manipleId + "/addCentury")
                .content(jacksonCenturyTester.write(century).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(manipleService, times(1)).loadManiple(manipleId);
        verify(centuryService, times(0)).findCenturyByName(anyString());
        verify(centuryService, times(0)).saveCentury(any());
    }

    @Test
    public void testRemoveCenturyFromManiple() throws Exception {
        when(manipleService.loadManiple(manipleId)).thenReturn(maniple);
        when(centuryService.loadCentury(centuryId)).thenReturn(century);
        mockMvc.perform(delete("/maniples/" + manipleId + "/deleteCentury/" + centuryId)).andExpect(status().isOk());
        verify(manipleService, times(1)).loadManiple(manipleId);
        verify(centuryService, times(1)).loadCentury(centuryId);
        verify(eventService, times(1)).deleteEventsByGroup(century);
        verify(centuryService, times(1)).deleteCentury(any());
    }

    @Test
    public void testRemoveCenturyFromNonExistingManiple() throws Exception {
        when(manipleService.loadManiple(anyLong())).thenReturn(null);
        when(centuryService.loadCentury(centuryId)).thenReturn(century);
        mockMvc.perform(delete("/maniples/" + manipleId + "/deleteCentury/" + centuryId)).andExpect(status().isBadRequest());
        verify(manipleService, times(1)).loadManiple(manipleId);
        verify(centuryService, times(1)).loadCentury(centuryId);
        verify(eventService, times(0)).deleteEventsByGroup(century);
        verify(centuryService, times(0)).deleteCentury(any());
    }

    @Test
    public void testRemoveNonExistingCenturyFromManiple() throws Exception {
        when(manipleService.loadManiple(manipleId)).thenReturn(maniple);
        when(centuryService.loadCentury(centuryId)).thenReturn(null);
        mockMvc.perform(delete("/maniples/" + manipleId + "/deleteCentury/" + centuryId)).andExpect(status().isBadRequest());
        verify(manipleService, times(1)).loadManiple(manipleId);
        verify(centuryService, times(1)).loadCentury(centuryId);
        verify(eventService, times(0)).deleteEventsByGroup(century);
        verify(centuryService, times(0)).deleteCentury(any());
    }

    @Test
    public void testRemoveNonExistingCenturyFromNonExistingManiple() throws Exception {
        when(manipleService.loadManiple(manipleId)).thenReturn(null);
        when(centuryService.loadCentury(centuryId)).thenReturn(null);
        mockMvc.perform(delete("/maniples/" + manipleId + "/deleteCentury/" + centuryId)).andExpect(status().isBadRequest());
        verify(manipleService, times(1)).loadManiple(manipleId);
        verify(centuryService, times(1)).loadCentury(centuryId);
        verify(eventService, times(0)).deleteEventsByGroup(century);
        verify(centuryService, times(0)).deleteCentury(any());
    }
    
    @After
    public void reset() {
        Mockito.reset(eventService);
        Mockito.reset(manipleService);
        Mockito.reset(centuryService);
    }

    @Configuration
    static class TestConfiguration {
        @Bean
        public EventService eventService() {
            return mock(EventService.class);
        }

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
            return new ManipleController(eventService(), manipleService(), centuryService());
        }
    }
}