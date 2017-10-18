package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Room;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by arvid on 18.10.17.
 */
public class RoomDAOImpl implements RoomDAO{

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * List all rooms currently stored in the database.
     *
     * @return a list of room entities. If no room was found an empty list is
     *         returned.
     */
    @SuppressWarnings("unchecked")
    public List<Room> listRooms() {
        List<Room> rooms = entityManager.createQuery("from Room").getResultList();
        return rooms;
    }

    public Room saveRoom(Room room) {
        try {
            entityManager.persist(room);
        } catch (ConstraintViolationException exception) {
            throw new RoomAlreadyExistsException();
        }
        return room;
    }

    /**
     * Returns the room identified by the given id.
     *
     * @param roomId
     *            The identifier.
     * @return the found entity.
     * @throws RoomNotFoundException
     *             if no room could be found for the given id.
     */
    public Room loadRoom(Long roomId) {
        // get session
        Room room = (Room) entityManager.find(Room.class, roomId);
        if (room == null) {
            throw new RoomNotFoundException();
        }
        return room;
    }

    /**
     * Deletes the room with the given id.
     *
     * @param roomId
     *            The identifier.
     * @throws RoomNotFoundException
     *             if no room could be found for the given id.
     */
    public void deleteRoom(Long roomId) {
        Room room = loadRoom(roomId);
        entityManager.remove(room);
    }

    public Room findRoomByNaturalId(String building, int number) {
        List<Room> rooms = entityManager.createQuery(
                "select r from Room r where r.building = :building " +
                        "and r.room_number = :room_number")
                .setParameter("building", building)
                .setParameter("number", number)
                .getResultList();
        if(rooms.isEmpty()){
            return null;
        }
        else return rooms.get(0);
    }
}
