package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.nordakademie.iaa.controller.SeminarGroupController;
import de.nordakademie.iaa.model.SeminarGroup;
import de.nordakademie.iaa.service.EventService;
import de.nordakademie.iaa.service.ManipleService;
import de.nordakademie.iaa.service.SeminarGroupService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
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
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SeminarGroupControllerTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private SeminarGroupController seminarGroupController;

    @Autowired
    private SeminarGroupService seminarGroupService;

    private MockMvc mockMvc;
    private JacksonTester<SeminarGroup> jacksonTester;
    private final SeminarGroup seminarGroup = new SeminarGroup("",20, 30);
    private final Long seminarGroupId = 1L;
    private final Long manipleId = seminarGroupId + 1;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(seminarGroupController).build();
        seminarGroup.setId(seminarGroupId);
    }

    @Test
    public void testListSeminarGroups() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
        List<SeminarGroup> seminarGroups = new ArrayList<>(Arrays.asList(seminarGroup));
        when(seminarGroupService.listSeminarGroups()).thenReturn(seminarGroups);
        MvcResult mvcResult = mockMvc.perform(get("/seminargroups"))
                .andExpect(status().isOk())
                .andReturn();
        verify(seminarGroupService, times(1)).listSeminarGroups();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<SeminarGroup> seminarGroupsResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<SeminarGroup>>() {
        });
        assertEquals(seminarGroups, seminarGroupsResponse);
    }

    @Test
    public void testSaveSeminarGroup() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
        String url = "/seminargroups";
        // SeminarGroup already existing
        when(seminarGroupService.findByName(seminarGroup.getName())).thenReturn(seminarGroup);
        mockMvc.perform(post(url).content(jacksonTester.write(seminarGroup).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(seminarGroupService, times(0)).saveSeminarGroup(any());
        // SeminarGroup not yet existing
        when(seminarGroupService.findByName(seminarGroup.getName())).thenReturn(null);
        mockMvc.perform(post(url).content(jacksonTester.write(seminarGroup).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.CREATED.value()));
        seminarGroup.setName("Max. " + seminarGroup.getMaximumNumberOfStudents() + " Teilnehmer");
        verify(seminarGroupService, times(1)).saveSeminarGroup(seminarGroup);
        // SeminarGroup not yet existing & saving failed
        doThrow(new RuntimeException()).when(seminarGroupService).saveSeminarGroup(any());
        seminarGroup.setName("");
        mockMvc.perform(post(url).content(jacksonTester.write(seminarGroup).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        seminarGroup.setName("Max. " + seminarGroup.getMaximumNumberOfStudents() + " Teilnehmer");
        verify(seminarGroupService, times(2)).saveSeminarGroup(seminarGroup);
    }

    @Test
    public void testUpdateSeminarGroup() throws Exception {
        final String url = "/seminargroups/" + seminarGroupId;
        JacksonTester.initFields(this, new ObjectMapper());
        // SeminarGroup not existing
        when(seminarGroupService.loadSeminarGroup(seminarGroupId)).thenReturn(null);
        mockMvc.perform(put(url).content(jacksonTester.write(seminarGroup).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(seminarGroupService, times(0)).saveSeminarGroup(seminarGroup);
        // SeminarGroup existing
        when(seminarGroupService.loadSeminarGroup(seminarGroupId)).thenReturn(seminarGroup);
        mockMvc.perform(put(url).content(jacksonTester.write(seminarGroup).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(seminarGroupService, times(1)).saveSeminarGroup(seminarGroup);
        // SeminarGroup existing & updating failed
        doThrow(new RuntimeException()).when(seminarGroupService).saveSeminarGroup(any());
        mockMvc.perform(put(url).content(jacksonTester.write(seminarGroup).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(seminarGroupService, times(2)).saveSeminarGroup(seminarGroup);
    }

    @Test
    public void testDeleteSeminarGroup() throws Exception {
        InOrder inOrder = inOrder(seminarGroupService, eventService);
        final String url = "/seminargroups/" + seminarGroupId;

        // SeminarGroup not existing
        when(seminarGroupService.loadSeminarGroup(anyLong())).thenReturn(null);
        mockMvc.perform(delete(url)).andExpect(status().isBadRequest());
        verify(eventService, times(0)).deleteEventsByGroup(any());
        verify(seminarGroupService, times(0)).deleteSeminarGroup(anyLong());

        // SeminarGroup existing
        when(seminarGroupService.loadSeminarGroup(seminarGroupId)).thenReturn(seminarGroup);
        mockMvc.perform(delete(url)).andExpect(status().isOk());
        inOrder.verify(eventService, times(1)).deleteEventsByGroup(seminarGroup);
        inOrder.verify(seminarGroupService, times(1)).deleteSeminarGroup(seminarGroupId);
    }


    @After
    public void reset() {
        Mockito.reset(eventService);
        Mockito.reset(seminarGroupService);
    }

    @Configuration
    static class TestConfiguration {
        @Bean
        public EventService eventService() {
            return mock(EventService.class);
        }

        @Bean
        public SeminarGroupService seminarGroupService() {
            return mock(SeminarGroupService.class);
        }

        @Bean
        public ManipleService manipleService() {
            return mock(ManipleService.class);
        }

        @Bean
        public SeminarGroupController seminarGroupController() {
            return new SeminarGroupController(eventService(), seminarGroupService(), manipleService());
        }
    }
}