package de.nordakademie.iaa.service.exception;

/**
 * Exception throws if a room could not be found.
 *
 * @author Stephan Anft
 */
public class NotEnoughChangeoverTimeProvidedException extends Exception {

    /**
     * Default constructor.
     */
    public NotEnoughChangeoverTimeProvidedException() {
        super();
    }

    /**
     * Constructor with message.
     *
     * @param message The message.
     */
    public NotEnoughChangeoverTimeProvidedException(String message) {
        super(message);
    }
}
