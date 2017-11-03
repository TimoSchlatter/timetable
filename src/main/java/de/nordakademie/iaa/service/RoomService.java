package de.nordakademie.iaa.service;

import de.nordakademie.iaa.model.Room;
import de.nordakademie.iaa.model.RoomType;
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

    /**
     * Retrieves a room by its field and number.
     * @param building the identifier.
     * @param number the identifier.
     * @return the found room or null if no room was found with given identifiers.
     */
    Room findByBuildingAndNumber(String building, String number);

    /**
     * Retrieves a room by its roomType.
     * @param roomType the identifier.
     * @return the found room or null if no room was found with given identifier.
     */
    List<Room> findByRoomType(RoomType roomType);
}
