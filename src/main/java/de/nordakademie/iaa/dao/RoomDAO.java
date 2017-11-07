package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Room;
import de.nordakademie.iaa.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by arvid on 18.10.17.
 */

public interface RoomDAO extends JpaRepository<Room,Long>, BaseDAO<Room, Long> {

    /**
     *
     * @param building
     * @param number
     * @return
     */
    Room findByBuildingAndNumber(String building, String number);

    /**
     *
     * @param roomType
     * @return
     */
    List<Room> findByRoomType(RoomType roomType);
}
