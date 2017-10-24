package de.nordakademie.iaa.test.dao;

import de.nordakademie.iaa.ApplicationConfig;
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

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ApplicationConfig.class})
@Transactional
public class RoomDAOTest {

    @Autowired
    private RoomDAO roomDAO;
    @PersistenceContext
    private EntityManager entityManager;

    private Room room;

    @Before
    public void setupData() {
        room = new Room("T", 42, 123, RoomType.COMPUTERRAUM);
        roomDAO.save(room);
    }

    @After
    public void tearDown() {
        entityManager.clear();
    }

    @Test
    public void testFindOne() {
        Room room = roomDAO.findOne(this.room.getId());

        assertEquals(this.room.getNumber(), room.getNumber());
        assertEquals(this.room.getBuilding(), room.getBuilding());
        assertEquals(this.room.getMaxSeats(), room.getMaxSeats());
        assertEquals(this.room.getRoomType(), room.getRoomType());
    }

    @Test
    public void testFindAll() {
        List<Room> rooms = roomDAO.findAll();

        assertEquals(1, rooms.size());

        for (Room room : rooms) {
            assertEquals(this.room.getNumber(), room.getNumber());
            assertEquals(this.room.getBuilding(), room.getBuilding());
            assertEquals(this.room.getMaxSeats(), room.getMaxSeats());
            assertEquals(this.room.getRoomType(), room.getRoomType());
        }
    }

    @Test
    public void testDelete() {
        roomDAO.delete(this.room);
        List<Room> rooms = roomDAO.findAll();

        assertTrue(rooms.isEmpty());

    }

    @Test
    public void testDeleteById() {
        roomDAO.deleteById(this.room.getId());
        List<Room> rooms = roomDAO.findAll();

        assertTrue(rooms.isEmpty());
    }

    @Test
    public void testFindRoomByBuildingAndNumber() {
        Room room = roomDAO.findRoomByBuildingAndNumber(this.room.getBuilding(), this.room.getNumber());

        assertEquals(this.room.getNumber(), room.getNumber());
        assertEquals(this.room.getBuilding(), room.getBuilding());
        assertEquals(this.room.getMaxSeats(), room.getMaxSeats());
        assertEquals(this.room.getRoomType(), room.getRoomType());
    }

    @Test
    public void testFindRoomsByType() {
        List<Room> rooms = roomDAO.findRoomsByType(this.room.getRoomType());

        assertEquals(1, rooms.size());

        for (Room room : rooms) {
            assertEquals(this.room.getNumber(), room.getNumber());
            assertEquals(this.room.getBuilding(), room.getBuilding());
            assertEquals(this.room.getMaxSeats(), room.getMaxSeats());
            assertEquals(this.room.getRoomType(), room.getRoomType());
        }
    }
}
