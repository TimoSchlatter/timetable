package controller;

import de.nordakademie.iaa.controller.CenturyController;
import de.nordakademie.iaa.model.Century;
import de.nordakademie.iaa.service.CenturyService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static java.lang.Math.toIntExact;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CenturyControllerTest {

    @Autowired
    private CenturyController centuryController;

    @Autowired
    private CenturyService centuryService;

    private Century century = new Century("I14", 30);
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(centuryController).build();
        century.setId(1L);
    }

    @Test
    public void testListCenturys() throws Exception {
        when(centuryService.listCenturies()).thenReturn(Arrays.asList(century));
        mockMvc.perform(get("/centuries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(toIntExact(century.getId()))))
                .andExpect(jsonPath("$.[0].minChangeoverTime", is(century.getMinChangeoverTime())))
                .andExpect(jsonPath("$.[0].name", is(century.getName())))
                .andExpect(jsonPath("$.[0].numberOfStudents", is(century.getNumberOfStudents())));
        verify(centuryService, times(1)).listCenturies();
    }

    @Test
    public void testDeleteCentury() throws Exception {
        mockMvc.perform(delete("/centuries/" + century.getId())).andExpect(status().isOk());
        verify(centuryService, times(1)).deleteCentury(century.getId());
        doThrow(new EntityNotFoundException()).when(centuryService).deleteCentury(anyLong());
        mockMvc.perform(delete("/centuries/" + century.getId())).andExpect(status().isNotFound());
        verify(centuryService, times(2)).deleteCentury(century.getId());
    }

    @After
    public void reset() {
        Mockito.reset(centuryService);
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public CenturyService centuryService() {
            return mock(CenturyService.class);
        }

        @Bean
        public CenturyController centuryController() {
            return new CenturyController(centuryService());
        }
    }
}