package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.nordakademie.iaa.controller.EventController;
import de.nordakademie.iaa.model.*;
import de.nordakademie.iaa.service.DocentService;
import de.nordakademie.iaa.service.EventService;
import de.nordakademie.iaa.service.GroupService;
import de.nordakademie.iaa.service.RoomService;
import de.nordakademie.iaa.service.exception.RoomTooSmallForGroupException;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
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
    private DocentService docentService;

    @Autowired
    private EventService eventService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private RoomService roomService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private JacksonTester<Event> jacksonTester;
    private final Room room = new Room(15, "A", 40, "001", RoomType.LECTUREROOM);
    private final Set<Room> rooms = new HashSet<>(Arrays.asList(room));
    private final Docent docent = new Docent("a@a.com", "Ed", "Sy", "0115273487", "Dr.", true, 20);
    private final Set<Docent> docents = new HashSet<>(Arrays.asList(docent));
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
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.registerModule(new JavaTimeModule());
        JacksonTester.initFields(this, objectMapper);
        event.setId(eventId);
    }

    @Test
    public void testListEvents() throws Exception {
        List<Event> events = new ArrayList<>(Arrays.asList(event));
        when(eventService.listEvents()).thenReturn(events);
        MvcResult mvcResult = mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andReturn();
        verify(eventService, times(1)).listEvents();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<Event> eventsResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<Event>>() {
        });
        assertEquals(events, eventsResponse);
    }

    @Test
    public void testSaveEvent() throws Exception {
        InOrder inOrder = inOrder(eventService);
        List<String> collisions = Arrays.asList("Test1", "Test2");
        String url = "/events";
        // Event already existing
        when(eventService.findEventByDateAndStartTimeAndEndTimeAndGroup(date, startTime, endTime, group))
                .thenReturn(event);
        when(eventService.findCollisions(event)).thenReturn(collisions);
        MvcResult mvcResult = mockMvc.perform(post(url).content(jacksonTester.write(event).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        inOrder.verify(eventService, times(1)).findCollisions(event);
        inOrder.verify(eventService, times(0)).saveEvent(any());
        inOrder.verify(eventService, times(0)).findEventByDateAndStartTimeAndEndTimeAndGroup(any(), any(), any(), any());
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<String> collisionsResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<String>>() {});
        assertEquals(collisions, collisionsResponse);

        // Event not yet existing & collisions
        when(eventService.findEventByDateAndStartTimeAndEndTimeAndGroup(date, startTime, endTime, group))
                .thenReturn(null);
        when(eventService.findCollisions(event)).thenReturn(collisions);
        mvcResult = mockMvc.perform(post(url).content(jacksonTester.write(event).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        inOrder.verify(eventService, times(1)).findCollisions(event);
        inOrder.verify(eventService, times(0)).findEventByDateAndStartTimeAndEndTimeAndGroup(date, startTime, endTime, group);
        inOrder.verify(eventService, times(0)).saveEvent(event);
        jsonResponse = mvcResult.getResponse().getContentAsString();
        collisionsResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<String>>() {});
        assertEquals(collisions, collisionsResponse);

        // Event not yet existing & collisions & ignoreCollisions
        when(eventService.findEventByDateAndStartTimeAndEndTimeAndGroup(date, startTime, endTime, group))
                .thenReturn(null);
        when(eventService.findCollisions(event)).thenReturn(collisions);
        mockMvc.perform(post(url + "?ignoreCollisions=true").content(jacksonTester.write(event).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        inOrder.verify(eventService, times(1)).findCollisions(event);
        inOrder.verify(eventService, times(1))
                .findEventByDateAndStartTimeAndEndTimeAndGroup(date, startTime, endTime, group);
        inOrder.verify(eventService, times(1)).saveEvent(event);

        // Event not yet existing & no collisions
        when(eventService.findEventByDateAndStartTimeAndEndTimeAndGroup(date, startTime, endTime, group))
                .thenReturn(null);
        when(eventService.findCollisions(event)).thenReturn(new ArrayList<>());
        mockMvc.perform(post(url).content(jacksonTester.write(event).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.CREATED.value()));
        inOrder.verify(eventService, times(1)).findCollisions(event);
        inOrder.verify(eventService, times(1)).findEventByDateAndStartTimeAndEndTimeAndGroup(date, startTime, endTime, group);
        inOrder.verify(eventService, times(1)).saveEvent(event);

        // Event not yet existing & no collisions & saving failed
        doThrow(new RoomTooSmallForGroupException()).when(eventService).saveEvent(any());
        when(eventService.findCollisions(event)).thenReturn(new ArrayList<>());
        mockMvc.perform(post(url).content(jacksonTester.write(event).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        inOrder.verify(eventService, times(1)).saveEvent(event);
    }

    @Test
    public void testSaveAlreadyExistingEventSeries() throws Exception {
        // Events already existing
        int repeatWeeks = 10;
        List<String> collisions = Arrays.asList("Test1", "Test2");
        String url = "/events?repeatWeeks=" + repeatWeeks;
        when(eventService.findCollisions(any())).thenReturn(collisions);
        when(eventService.findEventByDateAndStartTimeAndEndTimeAndGroup(any(), any(), any(), any())).thenReturn(event);
        MvcResult mvcResult = mockMvc.perform(post(url).content(jacksonTester.write(event).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        verify(eventService, times(repeatWeeks)).findCollisions(any(Event.class));
        verify(eventService, times(0)).saveEvent(event);
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<String> collisionsResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<String>>() {});
        assertEquals(collisions.size() * repeatWeeks, collisionsResponse.size());
        // Ignore collisions
        mockMvc.perform(post(url + "&ignoreCollisions=true").content(jacksonTester.write(event).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(eventService, times(0)).saveEvent(event);
    }

    @Test
    public void testSaveNonExistingEventSeriesWithCollisions() throws Exception {
        // Events not yet existing & collisions
        int repeatWeeks = 10;
        List<String> collisions = Arrays.asList("Test1", "Test2");
        String url = "/events?repeatWeeks=" + repeatWeeks;
        when(eventService.findCollisions(any())).thenReturn(collisions);
        when(eventService.findEventByDateAndStartTimeAndEndTimeAndGroup(any(), any(), any(), any())).thenReturn(null);
        MvcResult mvcResult = mockMvc.perform(post(url).content(jacksonTester.write(event).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        verify(eventService, times(repeatWeeks)).findCollisions(any(Event.class));
        verify(eventService, times(0)).findEventByDateAndStartTimeAndEndTimeAndGroup(any(), any(), any(), any());
        verify(eventService, times(0)).saveEvent(any());
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<String> collisionsResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<String>>() {});
        assertEquals(collisions.size() * repeatWeeks, collisionsResponse.size());

        // Ignore collisions
        mockMvc.perform(post(url + "&ignoreCollisions=true").content(jacksonTester.write(event).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        verify(eventService, times(2* repeatWeeks)).findCollisions(any(Event.class));
        verify(eventService, times(repeatWeeks))
                .findEventByDateAndStartTimeAndEndTimeAndGroup(any(), any(), any(), any());
        verify(eventService, times(repeatWeeks)).saveEvent(any(Event.class));
    }

    @Test
    public void testSaveNonExistingEventSeriesWithoutCollisions() throws Exception {
        // Events not yet existing & no collisions
        int repeatWeeks = 10;
        String url = "/events?repeatWeeks=" + repeatWeeks;
        when(eventService.findEventByDateAndStartTimeAndEndTimeAndGroup(any(), any(), any(), any())).thenReturn(null);
        when(eventService.findCollisions(any())).thenReturn(new ArrayList<>());
        mockMvc.perform(post(url).content(jacksonTester.write(event).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.CREATED.value()));
        verify(eventService, times(repeatWeeks))
                .findEventByDateAndStartTimeAndEndTimeAndGroup(any(), any(), any(), any());
        verify(eventService, times(repeatWeeks)).saveEvent(any(Event.class));
    }

    @Test
    public void testSavingFailedForNonExistingEventSeriesWithoutCollisions() throws Exception {
        // Events not yet existing & no collisions & saving failed
        int repeatWeeks = 10;
        String url = "/events?repeatWeeks=" + repeatWeeks;
        when(eventService.findEventByDateAndStartTimeAndEndTimeAndGroup(any(), any(), any(), any())).thenReturn(null);
        when(eventService.findCollisions(any())).thenReturn(new ArrayList<>());
        doThrow(new RoomTooSmallForGroupException()).when(eventService).saveEvent(any());
        mockMvc.perform(post(url).content(jacksonTester.write(event).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(eventService, times(repeatWeeks))
                .findEventByDateAndStartTimeAndEndTimeAndGroup(any(), any(), any(), any());
        verify(eventService, times(repeatWeeks)).saveEvent(any(Event.class));
    }

    @Test
    public void testUpdateEvent() throws Exception {
        final String url = "/events/" + eventId;
        // Event not existing
        when(eventService.loadEvent(eventId)).thenReturn(null);
        mockMvc.perform(put(url).content(jacksonTester.write(event).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(eventService, times(0)).saveEvent(event);
        // Event existing & no collisions
        when(eventService.loadEvent(eventId)).thenReturn(event);
        when(eventService.findCollisions(event)).thenReturn(new ArrayList<>());
        mockMvc.perform(put(url).content(jacksonTester.write(event).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(eventService, times(1)).saveEvent(isA(Event.class));
        // Event existing & collisions
        List<String> collisions = Arrays.asList("Test1", "Test2");
        when(eventService.findCollisions(event)).thenReturn(collisions);
        MvcResult mvcResult = mockMvc.perform(put(url).content(jacksonTester.write(event).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<String> collisionsResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<String>>() {});
        assertEquals(collisions, collisionsResponse);
        verify(eventService, times(1)).saveEvent(isA(Event.class));
        // Event existing & no collisions & updating failed
        doThrow(new RoomTooSmallForGroupException()).when(eventService).saveEvent(any());
        when(eventService.findCollisions(event)).thenReturn(new ArrayList<>());
        mockMvc.perform(put(url).content(jacksonTester.write(event).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(eventService, times(2)).saveEvent(event);
    }

    @Test
    public void testDeleteEvent() throws Exception {
        final String url = "/events/" + event.getId();

        when(eventService.deleteEvent(eventId)).thenReturn(false);
        mockMvc.perform(delete(url)).andExpect(status().isBadRequest());

        when(eventService.deleteEvent(eventId)).thenReturn(true);
        mockMvc.perform(delete(url)).andExpect(status().isOk());
    }

    @Test
    public void testFindEventsByDocentId() throws Exception {
        final Long docentId = 1L;
        final String url = "/events/findByDocent?id=" + docentId;

        // Docent not existing
        when(docentService.loadDocent(anyLong())).thenReturn(null);
        MvcResult mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();
        verify(eventService, times(0)).findEventsByDocent(any());
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<Event> eventsResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<Event>>() {
        });
        assertEquals(new ArrayList<Event>(), eventsResponse);

        // Docent existing
        when(docentService.loadDocent(docentId)).thenReturn(docent);
        when(eventService.findEventsByDocent(docent)).thenReturn(Arrays.asList(event));
        mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();
        verify(eventService, times(1)).findEventsByDocent(any());
        jsonResponse = mvcResult.getResponse().getContentAsString();
        eventsResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<Event>>() {
        });
        assertEquals(Arrays.asList(event), eventsResponse);
    }

    @Test
    public void testFindEventsByGroupId() throws Exception {
        final String url = "/events/findByGroup?id=1";

        // Group not existing
        when(groupService.loadGroup(anyLong())).thenReturn(null);
        MvcResult mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();
        verify(eventService, times(0)).findEventsByGroup(any());
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<Event> eventsResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<Event>>() {
        });
        assertEquals(new ArrayList<Event>(), eventsResponse);

        // Group existing
        when(groupService.loadGroup(anyLong())).thenReturn(group);
        when(eventService.findEventsByGroup(group)).thenReturn(Arrays.asList(event));
        mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();
        verify(eventService, times(1)).findEventsByGroup(any());
        jsonResponse = mvcResult.getResponse().getContentAsString();
        eventsResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<Event>>() {
        });
        assertEquals(Arrays.asList(event), eventsResponse);
    }

    @Test
    public void testFindEventsByRoomId() throws Exception {
        final String url = "/events/findByRoom?id=1";

        // Room not existing
        when(roomService.loadRoom(anyLong())).thenReturn(null);
        MvcResult mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();
        verify(eventService, times(0)).findEventsByRoom(any());
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<Event> eventsResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<Event>>() {
        });
        assertEquals(new ArrayList<Event>(), eventsResponse);

        // Room existing
        when(roomService.loadRoom(anyLong())).thenReturn(room);
        when(eventService.findEventsByRoom(room)).thenReturn(Arrays.asList(event));
        mvcResult = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn();
        verify(eventService, times(1)).findEventsByRoom(any());
        jsonResponse = mvcResult.getResponse().getContentAsString();
        eventsResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<Event>>() {
        });
        assertEquals(Arrays.asList(event), eventsResponse);
    }

    @After
    public void reset() {
        Mockito.reset(docentService);
        Mockito.reset(eventService);
        Mockito.reset(groupService);
        Mockito.reset(roomService);
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public DocentService docentService() {
            return mock(DocentService.class);
        }

        @Bean
        public EventService eventService() {
            return mock(EventService.class);
        }

        @Bean
        public GroupService groupService() {
            return mock(GroupService.class);
        }

        @Bean
        public RoomService roomService() {
            return mock(RoomService.class);
        }

        @Bean
        public EventController eventController() {
            return new EventController(docentService(), eventService(),
                    groupService(), roomService());
        }
    }
}