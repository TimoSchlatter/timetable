package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.dao.common.Operations;
import de.nordakademie.iaa.model.Room;
import de.nordakademie.iaa.model.RoomType;

import java.util.List;

/**
 * Created by arvid on 18.10.17.
 */

public interface RoomDAO extends Operations<Room> {
    Room findRoomByBuildingAndNumber(String building, String number);
    List<Room> findRoomsByType(RoomType type);
}
