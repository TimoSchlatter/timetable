package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Room;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by arvid on 18.10.17.
 */
public class RoomDAOImpl extends AbstractDAO <Room> implements IRoomDAO {

    public RoomDAOImpl() {
        setClass(Room.class);
    }

    public Room findRoomByBuildingAndNumber(String building, int number) {
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
