package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.nordakademie.iaa.controller.RoomController;
import de.nordakademie.iaa.model.Room;
import de.nordakademie.iaa.model.RoomType;
import de.nordakademie.iaa.service.RoomService;
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
public class RoomControllerTest {

    @Autowired
    private RoomController roomController;

    @Autowired
    private RoomService roomService;

    private JacksonTester<Room> jacksonTester;
    private Room room = new Room(20, "T", 42, "999", RoomType.COMPUTERROOM);
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(this.roomController).build();
        room.setId(42L);
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
        mockMvc.perform(post("/rooms").content(jacksonTester.write(room).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(this.roomService, times(1)).saveRoom(room);
        doThrow(new RuntimeException()).when(roomService).saveRoom(any());
        mockMvc.perform(post("/rooms").content(jacksonTester.write(room).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(this.roomService, times(2)).saveRoom(room);
    }

    @Test
    public void testDeleteRoom() throws Exception {
        mockMvc.perform(delete("/rooms/" + room.getId())).andExpect(status().isOk());
        verify(this.roomService, times(1)).deleteRoom(room.getId());
        doThrow(new EntityNotFoundException()).when(roomService).deleteRoom(anyLong());
        mockMvc.perform(delete("/rooms/" + room.getId())).andExpect(status().isNotFound());
        verify(this.roomService, times(2)).deleteRoom(room.getId());
    }

    @After
    public void reset() {
        Mockito.reset();
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public RoomService roomService() {
            return mock(RoomService.class);
        }

        @Bean
        public RoomController roomController() {
            return new RoomController(roomService());
        }
    }
}