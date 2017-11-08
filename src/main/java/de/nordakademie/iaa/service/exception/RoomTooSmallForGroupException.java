package de.nordakademie.iaa.service.exception;

import de.nordakademie.iaa.model.Group;
import de.nordakademie.iaa.model.Room;

/**
 * Exception throws if an event was tried to be saved with a room which is too small for the event's group.
 *
 * @author Timo Schlatter
 */
public class RoomTooSmallForGroupException extends Exception {
    /**
     * Default constructor.
     */
    public RoomTooSmallForGroupException() {
        super();
    }

    /**
     * Constructor with message.
     *
     * @param room The room which is too small for the group.
     * @param group THe group that does not fit in the room.
     */
    public RoomTooSmallForGroupException(Room room, Group group) {
        super("Der " + room  + " ist zu klein für " + group);
    }
}
