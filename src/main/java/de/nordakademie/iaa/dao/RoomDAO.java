package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Room;
import de.nordakademie.iaa.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by arvid on 18.10.17.
 */

public interface RoomDAO extends JpaRepository<Room,Long>, BaseDAO<Room, Long> {

    @Query("SELECT r FROM Room r WHERE r.building = :building AND r.number = :number")
    Room findRoomByBuildingAndNumber(@Param("building")String building, @Param("number")String number);

    @Query("SELECT r FROM Room r WHERE r.roomType = :type")
    List<Room> findRoomsByType(@Param("type")RoomType type);
}
