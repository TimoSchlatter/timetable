package de.nordakademie.iaa.dao.impl;

import de.nordakademie.iaa.dao.common.DAO;
import de.nordakademie.iaa.dao.RoomDAO;
import de.nordakademie.iaa.model.Room;
import de.nordakademie.iaa.model.RoomType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by arvid on 18.10.17.
 */
@Repository
public class RoomDAOImpl extends DAO<Room> implements RoomDAO {

    public RoomDAOImpl() {
        setClass(Room.class);
    }

    public Room findRoomByBuildingAndNumber(String building, String number) {
        List<Room> rooms = entityManager.createQuery(
                "select r from Room r where r.building = :building " +
                        "and r.number = :number")
                .setParameter("building", building)
                .setParameter("number", number)
                .getResultList();
        if(rooms.isEmpty()){
            return null;
        }
        else return rooms.get(0);
    }

    @Override
    public List<Room> findRoomsByType(RoomType type) {
        List<Room> rooms = entityManager.createQuery(
                "select r from Room r where r.roomType = :type ")
                .setParameter("type", type)
                .getResultList();
        return rooms;
    }
}
