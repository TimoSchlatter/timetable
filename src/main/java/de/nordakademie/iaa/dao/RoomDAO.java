package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Room;
import de.nordakademie.iaa.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * DAO for room entities.
 *
 * @author Arvid Ottenberg
 */
public interface RoomDAO extends JpaRepository<Room, Long> {

    /**
     * Finds a room by its building and number.
     *
     * @param building building the room is located in.
     * @param number   number of the room.
     * @return the room with the given building and number.
     */
    Room findByBuildingAndNumber(String building, String number);

    /**
     * Finds all rooms with a specific room type.
     *
     * @param roomType the type of room.
     * @return rooms with the given room type.
     */
    List<Room> findByRoomType(RoomType roomType);
}
