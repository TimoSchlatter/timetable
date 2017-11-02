package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.nordakademie.iaa.controller.DocentController;
import de.nordakademie.iaa.model.Docent;
import de.nordakademie.iaa.service.DocentService;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class DocentControllerTest {

    @Autowired
    private DocentController docentController;

    @Autowired
    private DocentService docentService;

    private JacksonTester<Docent> jacksonTester;
    private Docent docent = new Docent("test@docent.com", "John", "Doe", "0123123123", "Dr.Dr.", true, 20);
    private final Long docentId = 1L;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(docentController).build();
        docent.setId(docentId);
    }

    @Test
    public void testListDocents() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
        List<Docent> docents = new ArrayList<>(Arrays.asList(docent));
        when(docentService.listDocents()).thenReturn(docents);
        MvcResult mvcResult = mockMvc.perform(get("/docents"))
                .andExpect(status().isOk())
                .andReturn();
        verify(docentService, times(1)).listDocents();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<Docent> docentsResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<Docent>>() {});
        assertEquals(docents, docentsResponse);
    }

    @Test
    public void testSaveDocent() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
        // Docent already existing
        when(docentService.findByForenameAndSurname(anyString(), anyString())).thenReturn(docent);
        mockMvc.perform(post("/docents").content(jacksonTester.write(docent).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(docentService, times(0)).saveDocent(docent);
        // Docent not yet existing
        when(docentService.findByForenameAndSurname(anyString(), anyString())).thenReturn(null);
        mockMvc.perform(post("/docents").content(jacksonTester.write(docent).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.CREATED.value()));
        verify(docentService, times(1)).saveDocent(docent);
        // Docent not yet existing & saving failed
        doThrow(new RuntimeException()).when(docentService).saveDocent(any());
        mockMvc.perform(post("/docents").content(jacksonTester.write(docent).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(docentService, times(2)).saveDocent(docent);
    }

    @Test
    public void testUpdateDocent() throws Exception {
        final String url = "/docents/" + docentId;
        JacksonTester.initFields(this, new ObjectMapper());
        // Docent not existing
        when(docentService.loadDocent(docentId)).thenReturn(null);
        mockMvc.perform(put(url).content(jacksonTester.write(docent).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(docentService, times(0)).saveDocent(docent);
        // Docent existing
        when(docentService.loadDocent(docentId)).thenReturn(docent);
        mockMvc.perform(put(url).content(jacksonTester.write(docent).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(docentService, times(1)).saveDocent(docent);
        // Docent existing & updating failed
        doThrow(new RuntimeException()).when(docentService).saveDocent(any());
        mockMvc.perform(put(url).content(jacksonTester.write(docent).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(docentService, times(2)).saveDocent(docent);
    }

    @Test
    public void testDeleteDocent() throws Exception {
        mockMvc.perform(delete("/docents/" + docent.getId())).andExpect(status().isOk());
        verify(docentService, times(1)).deleteDocent(docent.getId());
        doThrow(new EntityNotFoundException()).when(docentService).deleteDocent(anyLong());
        mockMvc.perform(delete("/docents/" + docent.getId())).andExpect(status().isNotFound());
        verify(docentService, times(2)).deleteDocent(docent.getId());
    }

    @After
    public void reset() {
        Mockito.reset(docentService);
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public DocentService docentService() {
            return mock(DocentService.class);
        }

        @Bean
        public DocentController docentController() {
            return new DocentController(docentService());
        }
    }
}