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

    public NotEnoughChangeoverTimeProvidedException(Object objectWithoutEnoughChangeoverTime, int needed, int provided) {
        super(objectWithoutEnoughChangeoverTime + " needs at least " + needed + "min changeover time. Provided: "
                + provided);
    }
}
