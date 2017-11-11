package service;


import de.nordakademie.iaa.dao.RoomDAO;
import de.nordakademie.iaa.model.Room;
import de.nordakademie.iaa.model.RoomType;
import de.nordakademie.iaa.service.RoomService;

import de.nordakademie.iaa.service.impl.RoomServiceImpl;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomDAO roomDAO;

    private final Long id = 1L;
    private final String building = "A";
    private final String number = "123";
    private final RoomType roomType = RoomType.LECTUREROOM;
    private final Room room = new Room(20, building, 30, number, roomType);

    @Test
    public void testSaveRoom() {
        roomService.saveRoom(room);
        verify(roomDAO, times(1)).save(room);
    }

    @Test
    public void testListRooms() {
        roomService.listRooms();
        verify(roomDAO, times(1)).findAll();
    }

    @Test
    public void testLoadRoom() {
        roomService.loadRoom(id);
        verify(roomDAO, times(1)).findOne(id);
    }

    @Test
    public void testDeleteNonExistingRoom() {
        when(roomDAO.findOne(id)).thenReturn(null);
        assertFalse(roomService.deleteRoom(id));
        verify(roomDAO, times(0)).deleteById(anyLong());
    }

    @Test
    public void testDeleteExistingRoom() {
        when(roomDAO.findOne(id)).thenReturn(room);
        assertTrue(roomService.deleteRoom(id));
        verify(roomDAO, times(1)).delete(room);
    }

    @Test
    public void testFindByBuildingAndNumber() {
        when(roomDAO.findByBuildingAndNumber(building, number)).thenReturn(room);
        roomService.findByBuildingAndNumber(building, number);
        verify(roomDAO, times(1)).findByBuildingAndNumber(building, number);
    }

    @Test
    public void testFindByRoomType() {
        roomService.findByRoomType(roomType);
        verify(roomDAO, times(1)).findByRoomType(roomType);
    }

    @After
    public void reset() {
        Mockito.reset(roomDAO);
    }

    @Configuration
    static class TestConfiguration {

        @Bean
        public RoomService roomService() {
            return new RoomServiceImpl(roomDAO());
        }

        @Bean
        public RoomDAO roomDAO() {
            return mock(RoomDAO.class);
        }
    }
}