package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DAO for room entities.
 *
 * @author Arvid Ottenberg
 */
public interface RoomDAO extends JpaRepository<Room, Long> {

    /**
     * Retrieves a room by its building and number.
     *
     * @param building the identifier.
     * @param number   the identifier.
     * @return the found room or {@code null} if no room was found with the given identifiers.
     */
    Room findByBuildingAndNumber(String building, String number);
}