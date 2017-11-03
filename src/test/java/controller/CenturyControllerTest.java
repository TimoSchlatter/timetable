package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.nordakademie.iaa.controller.CenturyController;
import de.nordakademie.iaa.model.Century;
import de.nordakademie.iaa.service.CenturyService;
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
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CenturyControllerTest {

    @Autowired
    private CenturyController centuryController;

    @Autowired
    private CenturyService centuryService;

    private MockMvc mockMvc;
    private JacksonTester<Century> jacksonTester;
    private final Century century = new Century("I14", 30);
    private final Long centuryId = 1L;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(centuryController).build();
        century.setId(centuryId);
    }

    @Test
    public void testListCenturies() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
        List<Century> centuries = new ArrayList<>(Arrays.asList(century));
        when(centuryService.listCenturies()).thenReturn(centuries);
        MvcResult mvcResult = mockMvc.perform(get("/centuries"))
                .andExpect(status().isOk())
                .andReturn();
        verify(centuryService, times(1)).listCenturies();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<Century> centuriesResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<Century>>() {});
        assertEquals(centuries, centuriesResponse);
    }

    @Test
    public void testUpdateCentury() throws Exception {
        final String url = "/centuries/" + centuryId;
        JacksonTester.initFields(this, new ObjectMapper());
        // Century not existing
        when(centuryService.loadCentury(centuryId)).thenReturn(null);
        mockMvc.perform(put(url).content(jacksonTester.write(century).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(centuryService, times(0)).saveCentury(century);
        // Century existing
        when(centuryService.loadCentury(centuryId)).thenReturn(century);
        mockMvc.perform(put(url).content(jacksonTester.write(century).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(centuryService, times(1)).saveCentury(century);
        // Century existing & updating failed
        doThrow(new RuntimeException()).when(centuryService).saveCentury(any());
        mockMvc.perform(put(url).content(jacksonTester.write(century).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(centuryService, times(2)).saveCentury(century);
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