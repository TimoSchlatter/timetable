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

/**
 * Created by arvid on 24.10.17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@Transactional
public class RoomDAOTest {

    private RoomDAO roomDAO;

    @PersistenceContext
    private EntityManager entityManager;

    private Room room;

    @Autowired
    public void setRoomDAO(RoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }

    @Before
    public void setupData() {
        room = new Room(20,"A", 42, "101", RoomType.COMPUTERROOM);
        roomDAO.save(room);
    }

    @After
    public void tearDown() {
        entityManager.clear();
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
        compareRooms(room);
    }

    @Test
    public void testFindRoomsByType() {
        List<Room> rooms = roomDAO.findRoomsByType(this.room.getRoomType());
        assertEquals(1, rooms.size());
        rooms.forEach(this::compareRooms);
    }

    private void compareRooms(Room room) {
        assertEquals(this.room.getNumber(), room.getNumber());
        assertEquals(this.room.getMinChangeoverTime(), room.getMinChangeoverTime());
        assertEquals(this.room.getBuilding(), room.getBuilding());
        assertEquals(this.room.getMaxSeats(), room.getMaxSeats());
        assertEquals(this.room.getRoomType(), room.getRoomType());
    }
}
