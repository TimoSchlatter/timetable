package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.nordakademie.iaa.controller.RoomController;
import de.nordakademie.iaa.model.Event;
import de.nordakademie.iaa.model.Room;
import de.nordakademie.iaa.model.RoomType;
import de.nordakademie.iaa.service.EventService;
import de.nordakademie.iaa.service.RoomService;
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
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for RoomController class.
 *
 * @author Timo Schlatter
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class RoomControllerTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private RoomController roomController;

    @Autowired
    private RoomService roomService;

    private MockMvc mockMvc;
    private JacksonTester<Room> jacksonTester;
    private final String building = "T";
    private final String number = "999";
    private final Room room = new Room(20, building, 42, number, RoomType.COMPUTERROOM);
    private final Long roomId = 1L;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(this.roomController).build();
        room.setId(roomId);
    }

    @Test
    public void testListRooms() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
        List<Room> rooms = new ArrayList<>(Arrays.asList(room));
        when(this.roomService.listRooms()).thenReturn(rooms);
        MvcResult mvcResult = mockMvc.perform(get("/rooms"))
                .andExpect(status().isOk())
                .andReturn();
        verify(this.roomService, times(1)).listRooms();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<Room> roomsResponse = objectMapper.readValue(jsonResponse, new TypeReference<List<Room>>() {});
        assertEquals(rooms, roomsResponse);
    }

    @Test
    public void testSaveRoom() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
        String url = "/rooms";
        // Room already existing
        when(roomService.findRoomByBuildingAndNumber(building, number)).thenReturn(room);
        mockMvc.perform(post(url).content(jacksonTester.write(room).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(roomService, times(0)).saveRoom(room);
        // Room not yet existing
        when(roomService.findRoomByBuildingAndNumber(building, number)).thenReturn(null);
        mockMvc.perform(post(url).content(jacksonTester.write(room).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.CREATED.value()));
        verify(roomService, times(1)).saveRoom(room);
        // Room not yet existing & saving failed
        doThrow(new RuntimeException()).when(roomService).saveRoom(any());
        mockMvc.perform(post(url).content(jacksonTester.write(room).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(roomService, times(2)).saveRoom(room);
    }

    @Test
    public void testUpdateRoom() throws Exception {
        final String url = "/rooms/" + roomId;
        JacksonTester.initFields(this, new ObjectMapper());
        // Room not existing
        when(roomService.loadRoom(roomId)).thenReturn(null);
        mockMvc.perform(put(url).content(jacksonTester.write(room).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(roomService, times(0)).saveRoom(room);
        // Room existing
        when(roomService.loadRoom(roomId)).thenReturn(room);
        mockMvc.perform(put(url).content(jacksonTester.write(room).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(roomService, times(1)).saveRoom(room);
        // Room existing & updating failed
        doThrow(new RuntimeException()).when(roomService).saveRoom(any());
        mockMvc.perform(put(url).content(jacksonTester.write(room).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(roomService, times(2)).saveRoom(room);
    }

    @Test
    public void testDeleteRoom() throws Exception {
        String url = "/rooms/" + roomId;

        // Room not existing
        when(roomService.loadRoom(anyLong())).thenReturn(null);
        mockMvc.perform(delete(url)).andExpect(status().isBadRequest());
        verify(eventService, times(0)).findEventsByRoom(any());
        verify(eventService, times(0)).deleteEventsByGroup(any());
        verify(roomService, times(0)).deleteRoom(anyLong());

        // Room existing
        InOrder inOrder = inOrder(roomService, eventService);
        Event event1 = new Event();
        event1.setId(1L);
        event1.setRooms(new HashSet<>(Arrays.asList(room)));
        Event event2 = new Event();
        event2.setRooms(new HashSet<>(Arrays.asList(room, new Room(20, "A", 40, "001", RoomType.LECTUREROOM))));
        when(roomService.loadRoom(roomId)).thenReturn(room);
        when(eventService.findEventsByRoom(room)).thenReturn(Arrays.asList(event1, event2));
        mockMvc.perform(delete(url)).andExpect(status().isOk());
        inOrder.verify(eventService, times(1)).findEventsByRoom(room);
        inOrder.verify(eventService, times(1)).deleteEvent(event1.getId());
        inOrder.verify(roomService, times(1)).deleteRoom(roomId);
    }

    @After
    public void reset() {
        Mockito.reset(roomService);
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public EventService eventService() {
            return mock(EventService.class);
        }

        @Bean
        public RoomService roomService() {
            return mock(RoomService.class);
        }

        @Bean
        public RoomController roomController() {
            return new RoomController(eventService(), roomService());
        }
    }
}