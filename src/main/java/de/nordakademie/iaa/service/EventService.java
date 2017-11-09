package de.nordakademie.iaa.service;

import de.nordakademie.iaa.model.*;
import de.nordakademie.iaa.service.exception.RoomTooSmallForGroupException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public interface EventService {

    /**
     * Stores the given event into the database.
     *
     * @param event the event to be saved.
     */
    void saveEvent(Event event) throws RoomTooSmallForGroupException;

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
     * @return
     */
    boolean deleteEvent(Long id);

    /**
     * Retrieves a event by its date and startTime and endTime and group.
     *
     * @param date      the identifier.
     * @param startTime the identifier.
     * @param endTime   the identifier.
     * @param group     the identifier.
     * @return the found event or null if no event was found with given identifiers.
     */
    Event findEventByDateAndStartTimeAndEndTimeAndGroup(LocalDate date, LocalTime startTime, LocalTime endTime, Group group);

    List<Event> findEventsByTime(LocalDate date, LocalTime startTime, LocalTime endTime);

    void deleteEventsByGroup(Group group);

    void deleteEventsBySubject(Subject subject);

    /**
     * Find events by room
     *
     * @param room the room the events are happening in
     * @return events that happen in the given room
     */
    List<Event> findEventsByRoom(Room room);

    /**
     * Find events by docents
     *
     * @param docent the docent who is holding the event
     * @return events that are holded by the given docent
     */
    List<Event> findEventsByDocent(Docent docent);

    List<String> findCollisions(Event newEvent);

    List<String> findCollisions(Event newEvent, Event oldEvent);

    /**
     * Find events by group
     *
     * @param group group that attends the event
     * @return events that are attended by the given group
     */
    List<Event> findEventsByGroup(Group group);

}
