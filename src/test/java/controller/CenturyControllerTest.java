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
    public void testListCenturies() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
        List<Century> centuries = new ArrayList<>(Arrays.asList(century));
        when(this.centuryService.listCenturies()).thenReturn(centuries);
        MvcResult mvcResult = mockMvc.perform(get("/centuries"))
                .andExpect(status().isOk())
                .andReturn();
        verify(this.centuryService, times(1)).listCenturies();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<Century> centuriesResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<Century>>() {});
        assertEquals(centuries, centuriesResponse);
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