package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.nordakademie.iaa.Application;
import de.nordakademie.iaa.controller.VacantRoomController;
import de.nordakademie.iaa.model.Century;
import de.nordakademie.iaa.model.Event;
import de.nordakademie.iaa.model.Group;
import de.nordakademie.iaa.model.Room;
import de.nordakademie.iaa.service.EventService;
import de.nordakademie.iaa.service.GroupService;
import de.nordakademie.iaa.service.RoomService;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static de.nordakademie.iaa.model.RoomType.LECTUREROOM;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class VacantRoomControllerTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private VacantRoomController vacantRoomController;

    private MockMvc mockMvc;
    private final LocalDate date = LocalDate.of(2017, Month.DECEMBER, 17);
    private final LocalTime startTime = LocalTime.of(9, 15, 0);
    private final LocalTime endTime = LocalTime.of(11, 30, 0);
    private final int groupSize = 40;
    private final Long groupId = 1L;
    private final Group group = new Century("I14a", groupSize);
    private final Room room1 = new Room(15, "A", groupSize, "001", LECTUREROOM);
    private final Room room2 = new Room(15, "A", 35, "002", LECTUREROOM);
    private final Room room3 = new Room(15, "A", 35, "003", LECTUREROOM);
    private final Room room4 = new Room(15, "A", 35, "004", LECTUREROOM);
    private final Room room5 = new Room(15, "A", 35, "005", LECTUREROOM);
    private final Room room6 = new Room(15, "A", groupSize, "006", LECTUREROOM);
    private final Event event1 = new Event();
    private final Event event2 = new Event();

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(vacantRoomController).build();
        event1.setRooms(new HashSet<>(Arrays.asList(room1, room2)));
        event2.setRooms(new HashSet<>(Arrays.asList(room3, room4)));
    }

    @Test
    public void testListVacantRooms() throws Exception {
        when(roomService.listRooms()).thenReturn(Arrays.asList(room1, room2, room3, room4, room5, room6));
        when(eventService.findEventsByTime(date, startTime, endTime)).thenReturn(Arrays.asList(event1, event2));
        when(groupService.loadGroup(groupId)).thenReturn(group);
        MvcResult mvcResult = mockMvc.perform(get("/vacantRooms?date=" + date.toString() + "&startTime=" +
                startTime.format(Application.TIME_FORMATTER) + "&endTime=" + endTime.format(Application.TIME_FORMATTER) +
                "&groupId=" + groupId))
                .andExpect(status().isOk())
                .andReturn();
        verify(groupService, times(1)).loadGroup(groupId);
        verify(roomService, times(1)).listRooms();
        verify(eventService, times(1)).findEventsByTime(date, startTime, endTime);
        verify(eventService, times(0)).loadEvent(anyLong());
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<Room> roomsResponse = new ObjectMapper().readValue(jsonResponse, new TypeReference<List<Room>>() {});
        assertEquals(Arrays.asList(room6), roomsResponse);
    }

    @Test
    public void testListVacantRoomsWithEventId() throws Exception {
        Long eventId = groupId+1;
        when(roomService.listRooms()).thenReturn(Arrays.asList(room1, room2, room3, room4, room5, room6));
        when(eventService.findEventsByTime(date, startTime, endTime)).thenReturn(Arrays.asList(event1, event2));
        when(groupService.loadGroup(groupId)).thenReturn(group);
        when(eventService.loadEvent(eventId)).thenReturn(event1);
        MvcResult mvcResult = mockMvc.perform(get("/vacantRooms?date=" + date.toString() + "&startTime=" +
                startTime.format(Application.TIME_FORMATTER) + "&endTime=" + endTime.format(Application.TIME_FORMATTER) +
                "&groupId=" + groupId + "&eventId=" + eventId))
                .andExpect(status().isOk())
                .andReturn();
        verify(groupService, times(1)).loadGroup(groupId);
        verify(roomService, times(1)).listRooms();
        verify(eventService, times(1)).findEventsByTime(date, startTime, endTime);
        verify(eventService, times(1)).loadEvent(eventId);
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<Room> roomsResponse = new ObjectMapper().readValue(jsonResponse, new TypeReference<List<Room>>() {});
        assertEquals(Arrays.asList(room1, room6), roomsResponse);
    }

    @After
    public void reset() {
        Mockito.reset(eventService);
        Mockito.reset(groupService);
        Mockito.reset(roomService);
    }

    @Configuration
    static class TestConfiguration {
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
        public VacantRoomController vacantRoomController() {
            return new VacantRoomController(eventService(), groupService(), roomService());
        }
    }
}