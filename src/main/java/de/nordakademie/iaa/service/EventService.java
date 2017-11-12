package de.nordakademie.iaa.service;

import de.nordakademie.iaa.model.*;
import de.nordakademie.iaa.service.exception.RoomTooSmallForGroupException;
import de.nordakademie.iaa.service.exception.StartTimeAfterEndTimeException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Service interface for event entities.
 *
 * @author Timo Schlatter
 */
public interface EventService {

    /**
     * Stores the given event into the database.
     *
     * @param event the event to be saved.
     */
    void saveEvent(Event event) throws RoomTooSmallForGroupException, StartTimeAfterEndTimeException;

    /**
     * Lists all events currently stored in the database.
     *
     * @return a list of event entities. If no event was found an empty list is returned.
     */
    List<Event> listEvents();

    /**
     * Retrieves an event by its id.
     *
     * @param id the identifier.
     * @return the found event or {@code null} if no event was found with the given id.
     */
    Event loadEvent(Long id);

    /**
     * Deletes the event with the given id.
     *
     * @param id the identifier.
     * @return <tt>true</tt> if the event was deleted.
     */
    boolean deleteEvent(Long id);

    /**
     * Retrieves an event by its date, start time, end time and group.
     *
     * @param date      date on which the event is taking place.
     * @param startTime start time of the event.
     * @param endTime   end time of the event.
     * @param group     group that attends the event.
     * @return the found event or {@code null} if no event was found with the given identifier.
     */
    Event findEventByDateAndStartTimeAndEndTimeAndGroup(LocalDate date, LocalTime startTime, LocalTime endTime, Group group);

    /**
     * Retrieves events by their docent.
     *
     * @param docent the docent who is holding the event.
     * @return a list of events or an empty list if no event was found with the given identifier.
     */
    List<Event> findEventsByDocent(Docent docent);

    /**
     * Retrieves events by their group.
     *
     * @param group the group that attends the events.
     * @return a list of events or an empty list if no event was found with the given identifier.
     */
    List<Event> findEventsByGroup(Group group);

    /**
     * Retrieves events by their room.
     *
     * @param room the room where the events are taking place.
     * @return a list of events or an empty list if no event was found with the given identifier.
     */
    List<Event> findEventsByRoom(Room room);

    /**
     * Retrieves events that take place in a given period.
     *
     * @param date      the date on which the events are scheduled.
     * @param startTime the start time of the period when the events should take place.
     * @param endTime   the end time of the period when the events should take place.
     * @return a list of events or an empty list if no event was found with the given identifiers.
     */
    List<Event> findEventsByTime(LocalDate date, LocalTime startTime, LocalTime endTime);

    /**
     * Deletes events that are attended by a specific group.
     *
     * @param group the group that attends the events.
     */
    void deleteEventsByGroup(Group group);

    /**
     * Deletes events that are related to a specific subject.
     *
     * @param subject the subject the events are related to.
     */
    void deleteEventsBySubject(Subject subject);

    /**
     * Retrieves a list of collisions with concurrent events (e.g. when a room is already booked for another event).
     *
     * @param newEvent the event whose belongings will be checked for collisions.
     * @return a list of collisions or an empty list if no collisions could be determined.
     */
    List<String> findCollisions(Event newEvent);

    /**
     * Retrieves a list of collisions with concurrent events (e.g. when a room is already booked for another event).
     * By providing the <tt>oldEvent</tt> parameter no collisions will be detected with the <tt>newEvent</tt>.
     * This paramter is needed if <tt>newEvent</tt> is the updated version of <tt>oldEvent</tt>.
     *
     * @param newEvent the event whose belongings will be checked for collisions.
     * @param oldEvent the event which should not be treated as possibly colliding as it is the one getting an update.
     * @return a list of collisions or an empty list if no collisions could be determined.
     */
    List<String> findCollisions(Event newEvent, Event oldEvent);
}