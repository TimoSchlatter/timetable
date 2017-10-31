package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.nordakademie.iaa.controller.ManipleController;
import de.nordakademie.iaa.model.Century;
import de.nordakademie.iaa.model.Maniple;
import de.nordakademie.iaa.service.CenturyService;
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

import java.util.Arrays;

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
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(manipleController).build();
        maniple.setId(1L);
        century.setId(2L);
    }

    // TODO: Zenturie zur Kohorte hinzufügen und alles testen
    @Test
    public void testListManiples() throws Exception {
        when(manipleService.listManiples()).thenReturn(Arrays.asList(maniple));
        mockMvc.perform(get("/maniples"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(toIntExact(maniple.getId()))))
                .andExpect(jsonPath("$.[0].minChangeoverTime", is(maniple.getMinChangeoverTime())))
                .andExpect(jsonPath("$.[0].name", is(maniple.getName())));
        verify(manipleService, times(1)).listManiples();
    }

    @Test
    public void testDeleteManiple() throws Exception {
        mockMvc.perform(delete("/maniples/" + maniple.getId())).andExpect(status().isOk());
        verify(manipleService, times(1)).deleteManiple(maniple.getId());
        doThrow(new EntityNotFoundException()).when(manipleService).deleteManiple(anyLong());
        mockMvc.perform(delete("/maniples/" + maniple.getId())).andExpect(status().isNotFound());
        verify(manipleService, times(2)).deleteManiple(maniple.getId());
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