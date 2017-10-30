package service;


import de.nordakademie.iaa.dao.RoomDAO;
import de.nordakademie.iaa.model.Room;
import de.nordakademie.iaa.model.RoomType;
import de.nordakademie.iaa.service.RoomService;
import de.nordakademie.iaa.service.impl.RoomServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomDAO roomDAO;

    @Before
    public void setup() {
        Room room = new Room(20,"A",30,"123", RoomType.LECTUREROOM);
        Mockito.when(roomDAO.findByBuildingAndNumber("A","123")).thenReturn(room);
    }

    @Test()
    public void testFindRoomByBuildingAndNumber(){
        roomService.findByBuildingAndNumber("A","123");
    }

    @After
    public void verify() {
        Mockito.verify(roomDAO, VerificationModeFactory.times(1)).findByBuildingAndNumber(Mockito.anyString(),Mockito.anyString());
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
