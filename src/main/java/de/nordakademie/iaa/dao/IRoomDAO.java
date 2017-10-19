package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Room;

/**
 * Created by arvid on 18.10.17.
 */

public interface IRoomDAO {
    Room findRoomByNaturalId(String building, int number);
}
