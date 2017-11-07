package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.nordakademie.iaa.controller.EventController;
import de.nordakademie.iaa.model.*;
import de.nordakademie.iaa.service.EventService;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class EventControllerTest {

    @Autowired
    private EventController eventController;

    @Autowired
    private EventService eventService;

    private MockMvc mockMvc;
    private JacksonTester<Event> jacksonTester;
    private final Set<Room> rooms = new HashSet<>(Arrays.asList(new Room(15, "A", 40, "001", RoomType.LECTUREROOM)));
    private final Set<Docent> docents = new HashSet<>(Arrays.asList(new Docent("a@a.com", "Ed", "Sy", "0115273487", "Dr.", true, 20)));
    private final Group group = new Century("I14a", 30, 30);
    private final LocalDate date = LocalDate.of(2018, Month.APRIL, 1);
    private final LocalTime startTime = LocalTime.of(9, 30);
    private final LocalTime endTime = LocalTime.of(11, 30);
    private final Subject subject = new Subject(30, SubjectType.EXAM, new Course("Test", "I", 154));
    private final Event event = new Event(rooms, docents, group, date, startTime, endTime, subject);
    private final Long eventId = 1L;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
        event.setId(eventId);
    }

    @Test
    public void testListEvents() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.registerModule(new JavaTimeModule());
        JacksonTester.initFields(this, objectMapper);
        List<Event> events = new ArrayList<>(Arrays.asList(event));
        when(eventService.listEvents()).thenReturn(events);
        MvcResult mvcResult = mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andReturn();
        verify(eventService, times(1)).listEvents();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<Event> eventsResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<Event>>() {});
        assertEquals(events, eventsResponse);
    }

    @Test
    public void testSaveEvent() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.registerModule(new JavaTimeModule());
        JacksonTester.initFields(this, objectMapper);
        String url = "/events";
        // Event already existing
        when(eventService.findEventByDateAndStartTimeAndEndTimeAndGroup(date, startTime, endTime, group)).thenReturn(event);
        mockMvc.perform(post(url).content(jacksonTester.write(event).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(eventService, times(0)).saveEvent(event);
        // Event not yet existing
        when(eventService.findEventByDateAndStartTimeAndEndTimeAndGroup(date, startTime, endTime, group)).thenReturn(null);
        mockMvc.perform(post(url).content(jacksonTester.write(event).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.CREATED.value()));
        verify(eventService, times(1)).saveEvent(event);
        // Event not yet existing & saving failed
        doThrow(new RuntimeException()).when(eventService).saveEvent(any());
        mockMvc.perform(post(url).content(jacksonTester.write(event).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(eventService, times(2)).saveEvent(event);
    }

    @Test
    public void testUpdateEvent() throws Exception {
        final String url = "/events/" + eventId;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.registerModule(new JavaTimeModule());
        JacksonTester.initFields(this, objectMapper);
        // Event not existing
        when(eventService.loadEvent(eventId)).thenReturn(null);
        mockMvc.perform(put(url).content(jacksonTester.write(event).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(eventService, times(0)).saveEvent(event);
        // Event existing
        when(eventService.loadEvent(eventId)).thenReturn(event);
        mockMvc.perform(put(url).content(jacksonTester.write(event).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(eventService, times(1)).saveEvent(event);
        // Event existing & updating failed
        doThrow(new RuntimeException()).when(eventService).saveEvent(any());
        mockMvc.perform(put(url).content(jacksonTester.write(event).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(eventService, times(2)).saveEvent(event);
    }

    @Test
    public void testDeleteEvent() throws Exception {
        mockMvc.perform(delete("/events/" + event.getId())).andExpect(status().isOk());
        verify(eventService, times(1)).deleteEvent(event.getId());
        doThrow(new EntityNotFoundException()).when(eventService).deleteEvent(anyLong());
        mockMvc.perform(delete("/events/" + event.getId())).andExpect(status().isBadRequest());
        verify(eventService, times(2)).deleteEvent(event.getId());
    }

    @After
    public void reset() {
        Mockito.reset(eventService);
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public EventService eventService() {
            return mock(EventService.class);
        }

        @Bean
        public EventController eventController() {
            return new EventController(eventService());
        }
    }
}