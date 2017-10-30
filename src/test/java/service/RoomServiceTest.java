package service;


import de.nordakademie.iaa.dao.RoomDAO;
import de.nordakademie.iaa.model.Room;
import de.nordakademie.iaa.model.RoomType;
import de.nordakademie.iaa.service.RoomService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import de.nordakademie.iaa.service.impl.RoomServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.verification.VerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomDAO roomDAO;

    private final String building = "A";
    private final String number = "123";
    private final Room room = new Room(20, building, 30, number, RoomType.LECTUREROOM);

    @Test
    public void testSaveRoom() {
        roomService.saveRoom(room);
        Mockito.verify(roomDAO, times(1)).save(room);
    }

    @Test
    public void testLoadRoom() {
        final Long id = 123L;
        roomService.loadRoom(id);
        Mockito.verify(roomDAO, times(1)).findOne(id);
    }

    @Test
    public void testListRooms() {
        roomService.listRooms();
        Mockito.verify(roomDAO, times(1)).findAll();
    }

    @Test
    public void testFindByBuildingAndNumber() {
        Mockito.when(roomDAO.findByBuildingAndNumber(building, number)).thenReturn(room);
        roomService.findByBuildingAndNumber(building, number);
        Mockito.verify(roomDAO, times(1)).findByBuildingAndNumber(building, number);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteNonExistingRoom() throws EntityNotFoundException {
        final Long id = 123L;
        Mockito.when(roomDAO.findOne(anyLong())).thenReturn(null);
        roomService.deleteRoom(id);
    }

    @Test
    public void testDeleteExistingRoom() throws EntityNotFoundException {
        final Long id = 123L;
        Mockito.when(roomDAO.findOne(anyLong())).thenReturn(room);
        roomService.deleteRoom(id);
        Mockito.verify(roomDAO, times(1)).findOne(id);
        Mockito.verify(roomDAO, times(1)).delete(room);
    }

    @After
    public void clear() {
        Mockito.reset(roomDAO);
    }

    @Configuration
    static class AccountServiceTestContextConfiguration {

        @Bean
        public RoomService roomService() {
            return new RoomServiceImpl(roomDAO());
        }

        @Bean
        public RoomDAO roomDAO() {
            return Mockito.mock(RoomDAO.class);
        }
    }
}