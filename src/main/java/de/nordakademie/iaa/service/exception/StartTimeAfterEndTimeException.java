package de.nordakademie.iaa.service.exception;

import java.time.LocalTime;

/**
 * Exception thrown if an event's startTime is not before the event's endTime.
 *
 * @author Timo Schlatter
 */
public class StartTimeAfterEndTimeException extends Exception {

    /**
     * Default constructor.
     */
    public StartTimeAfterEndTimeException() {
        super();
    }

    /**
     * Constructor for providing extra information in the message.
     *
     * @param startTime the provided faulty startTime.
     * @param endTime   the provided endTime.
     */
    public StartTimeAfterEndTimeException(LocalTime startTime, LocalTime endTime) {
        super("Provided startTime (" + startTime + ") must be before endTime (" + endTime + ")");
    }
}
