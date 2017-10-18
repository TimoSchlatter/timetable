package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Room;
import java.util.List;

/**
 * Created by arvid on 18.10.17.
 */

public interface RoomDAO {
    Room saveRoom(Room room);
    Room loadRoom(Long roomId);
    void deleteRoom(Long roomId);
    List<Room> listRooms();
    Room findRoomByNaturalId(String building, int number);
}
