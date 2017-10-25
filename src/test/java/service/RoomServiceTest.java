package service;


import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class RoomServiceTest {
/**
    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomDAO roomDAO;

    @Before
    public void setup() {
        Room room = new Room(20,"A",30,"123", RoomType.LECTUREROOM);
        Mockito.when(roomDAO.findRoomByBuildingAndNumber("A","123")).thenReturn(room);
    }

    @Test()
    public void testFindRoomByBuildingAndNumber(){
        roomService.findRoomByBuildingAndNumber("A","123");
    }

    @After
    public void verify() {
        Mockito.verify(roomDAO, VerificationModeFactory.times(1)).findRoomByBuildingAndNumber(Mockito.anyString(),Mockito.anyString());
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
    **/
}
