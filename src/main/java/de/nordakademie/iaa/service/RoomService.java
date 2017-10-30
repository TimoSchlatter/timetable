package de.nordakademie.iaa.service;

import de.nordakademie.iaa.model.Room;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;

import java.util.List;


public interface RoomService {

    /**
     * Stores the given room into the database.
     *
     * @param room the room to be saved.
     */
    void saveRoom(Room room);

    /**
     * List all rooms currently stored in the database.
     *
     * @return a list of Room entities. If no room was found an empty list is
     * returned.
     */
    List<Room> listRooms();

    /**
     * Returns the room identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    Room loadRoom(Long id);

    /**
     * Deletes the room with the given id.
     *
     * @param id The identifier.
     * @throws EntityNotFoundException if no room could be found for the given id.
     */
    void deleteRoom(Long id) throws EntityNotFoundException;

    Room findByBuildingAndNumber(String building, String number);
}
