package de.nordakademie.iaa.service.exception;

/**
 * Exception thrown if the a given changeover time is below an entity's minimum.
 *
 * @author Timo Schlatter
 */
public class NotEnoughChangeoverTimeProvidedException extends Exception {

    /**
     * Constructor for providing extra information in the message.
     * @param objectWithoutEnoughChangeoverTime the object whose minChangeoverTime was not enough.
     * @param needed the minimum required changeover time for the entity.
     * @param provided the provided changeover time.
     */
    public NotEnoughChangeoverTimeProvidedException(Object objectWithoutEnoughChangeoverTime, int needed, int provided) {
        super(objectWithoutEnoughChangeoverTime + " needs at least " + needed + "min changeover time. Provided: "
                + provided);
    }
}