package de.nordakademie.iaa.service;

import de.nordakademie.iaa.model.Event;
import de.nordakademie.iaa.model.Group;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public interface EventService {

    /**
     * Stores the given event into the database.
     *
     * @param event the event to be saved.
     */
    void saveEvent(Event event);

    /**
     * List all events currently stored in the database.
     *
     * @return a list of Event entities. If no event was found an empty list is returned.
     */
    List<Event> listEvents();

    /**
     * Returns the event identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    Event loadEvent(Long id);

    /**
     * Deletes the event with the given id.
     *
     * @param id The identifier.
     * @throws EntityNotFoundException if no event could be found for the given id.
     */
    void deleteEvent(Long id) throws EntityNotFoundException;

    /**
     * Retrieves a event by its date and startTime and endTime and group.
     * @param date the identifier.
     * @param startTime the identifier.
     * @param endTime the identifier.
     * @param group the identifier.
     * @return the found event or null if no event was found with given identifiers.
     */
    Event findByDateAndStartTimeAndEndTimeAndGroup(LocalDate date, LocalTime startTime, LocalTime endTime, Group group);

}
