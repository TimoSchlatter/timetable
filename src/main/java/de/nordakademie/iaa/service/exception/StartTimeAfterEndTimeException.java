package de.nordakademie.iaa.service.exception;

import java.time.LocalTime;

/**
 * Exception thrown when an event's startTime is like or after the event's endTime.
 *
 * @author Stephan Anft
 */
public class StartTimeAfterEndTimeException extends Exception {

    /**
     * Default constructor.
     */
    public StartTimeAfterEndTimeException() {
        super();
    }

    /**
     * Constructor with startTime and endTime.
     * @param startTime the faulty startTime.
     * @param endTime the provided endTime.
     */
    public StartTimeAfterEndTimeException(LocalTime startTime, LocalTime endTime) {
        super("Provided startTime (" + startTime + ") must be before endTime (" + endTime + ")");
    }
}
