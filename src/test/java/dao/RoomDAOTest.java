package dao;

import de.nordakademie.iaa.Application;
import de.nordakademie.iaa.dao.RoomDAO;
import de.nordakademie.iaa.model.Room;
import de.nordakademie.iaa.model.RoomType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for RoomDAO class.
 *
 * @author Arvid Ottenberg
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class})
@Transactional
public class RoomDAOTest {

    @Autowired
    private RoomDAO roomDAO;

    @PersistenceContext
    private EntityManager entityManager;

    private Room room;

    @Before
    public void setupData() {
        room = new Room(20,"X", 42, "999", RoomType.COMPUTERROOM);
        roomDAO.save(room);
    }

    @Test
    public void testFindOne() {
        Room room = roomDAO.findOne(this.room.getId());
        compareRooms(room);
    }

    @Test
    public void testFindAll() {
        List<Room> rooms = roomDAO.findAll();
        assertEquals(1, rooms.size());
        rooms.forEach(this::compareRooms);
    }

    @Test
    public void testFindRoomByBuildingAndNumber() {
        Room room = roomDAO.findByBuildingAndNumber(this.room.getBuilding(), this.room.getNumber());
        compareRooms(room);
    }

    @Test
    public void testDelete() {
        roomDAO.delete(this.room);
        assertTrue(roomDAO.findAll().isEmpty());
    }

    @After
    public void tearDown() {
        entityManager.clear();
    }

    private void compareRooms(Room room) {
        assertEquals(this.room.getNumber(), room.getNumber());
        assertEquals(this.room.getMinChangeoverTime(), room.getMinChangeoverTime());
        assertEquals(this.room.getBuilding(), room.getBuilding());
        assertEquals(this.room.getMaxSeats(), room.getMaxSeats());
        assertEquals(this.room.getRoomType(), room.getRoomType());
    }
}