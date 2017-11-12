package de.nordakademie.iaa.service;

import de.nordakademie.iaa.model.Room;

import java.util.List;

/**
 * Service interface for room entities.
 *
 * @author Timo Schlatter
 */
public interface RoomService {

    /**
     * Stores the given room into the database.
     *
     * @param room the room to be saved.
     */
    void saveRoom(Room room);

    /**
     * Lists all rooms currently stored in the database.
     *
     * @return a list of room entities. If no room was found an empty list is returned.
     */
    List<Room> listRooms();

    /**
     * Retrieves a room by its id.
     *
     * @param id the identifier.
     * @return the found room or {@code null} if no room was found with the given id.
     */
    Room loadRoom(Long id);

    /**
     * Deletes the room with the given id.
     *
     * @param id the identifier.
     * @return <tt>true</tt> if the room was deleted.
     */
    boolean deleteRoom(Long id);

    /**
     * Retrieves a room by its building and number.
     *
     * @param building the identifier.
     * @param number   the identifier.
     * @return the found room or {@code null} if no room was found with the given identifiers.
     */
    Room findRoomByBuildingAndNumber(String building, String number);
}