package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.nordakademie.iaa.controller.RoomController;
import de.nordakademie.iaa.model.Room;
import de.nordakademie.iaa.model.RoomType;
import de.nordakademie.iaa.service.RoomService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import org.junit.After;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyLong;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class RoomControllerTest {

    @Autowired
    private RoomController roomController;
    @Autowired
    private RoomService roomService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private JacksonTester<Room> jacksonTester;

    private List<Room> rooms = new ArrayList<>();
    private Room room = new Room(20, "T", 42, "999",RoomType.COMPUTERROOM);

    @Test
    public void testListRooms() throws Exception {
        rooms.add(room);
        Mockito.when(this.roomService.listRooms()).thenReturn(rooms);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.roomController).build();
        mockMvc.perform(get("/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].minChangeoverTime", is(room.getMinChangeoverTime())))
                .andExpect(jsonPath("$.[0].building", is(room.getBuilding())))
                .andExpect(jsonPath("$.[0].maxSeats", is(room.getMaxSeats())))
                .andExpect(jsonPath("$.[0].number", is(room.getNumber())))
                .andExpect(jsonPath("$.[0].roomType", is(room.getRoomType().toString())));
        Mockito.verify(this.roomService, times(1)).listRooms();
    }

    @Test
    public void testSaveRoom() throws Exception {
        jacksonTester.initFields(this, objectMapper);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.roomController).build();
        mockMvc.perform(post("/rooms").content(jacksonTester.write(room).getJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Mockito.verify(this.roomService, times(1)).saveRoom(room);
    }

    @Test
    public void testDeleteRoom() throws Exception {
        room.setId(42L);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.roomController).build();
        mockMvc.perform(delete("/rooms/" + room.getId().toString())).andExpect(status().isOk());;
        Mockito.verify(this.roomService, times(1)).deleteRoom(room.getId());

        Mockito.doThrow(new EntityNotFoundException()).when(roomService).deleteRoom(anyLong());
        mockMvc.perform(delete("/rooms/" + room.getId().toString())).andExpect(status().isNotFound());
    }

    @After
    public void reset() {
        Mockito.reset();
    }

    @Configuration
    static class LoginControllerTestConfiguration {

        @Bean
        public RoomService roomService() {
            return Mockito.mock(RoomService.class);
        }

        @Bean
        public RoomController roomController() {
            return new RoomController(roomService());
        }
    }
}
