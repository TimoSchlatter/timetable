package de.nordakademie.iaa.service.exception;

import de.nordakademie.iaa.model.Group;
import de.nordakademie.iaa.model.Room;

/**
 * Exception thrown if an event was attempted to save with a room which is too small for the event's group.
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
     * Constructor for providing extra information in the message.
     *
     * @param room  the room which is too small for the group.
     * @param group the group that does not fit in the room.
     */
    public RoomTooSmallForGroupException(Room room, Group group) {
        super(room + " can not provide enough seats for " + group);
    }
}
