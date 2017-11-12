package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * DAO for event entities.
 *
 * @author Arvid Ottenberg
 */
public interface EventDAO extends JpaRepository<Event, Long> {

    /**
     * Retrieves an event by its date, start time, end time and group.
     *
     * @param date      date on which the event is taking place.
     * @param startTime start time of the event.
     * @param endTime   end time of the event.
     * @param group     group that attends the event.
     * @return the found event or {@code null} if no event was found with the given identifier.
     */
    Event findByDateAndStartTimeAndEndTimeAndGroup(LocalDate date, LocalTime startTime, LocalTime endTime, Group group);

    /**
     * Retrieves events by their docent.
     *
     * @param docent the docent who is holding the event.
     * @return a list of events or an empty list if no event was found with the given identifier.
     */
    List<Event> findByDocents(Docent docent);

    /**
     * Retrieves events by their group.
     *
     * @param group the group that attends the events.
     * @return a list of events or an empty list if no event was found with the given identifier.
     */
    List<Event> findByGroup(Group group);

    /**
     * Retrieves events by their room.
     *
     * @param room the room where the events are taking place.
     * @return a list of events or an empty list if no event was found with the given identifier.
     */
    List<Event> findByRooms(Room room);

    /**
     * Retrieves events that take place in a given period.
     *
     * @param date      the date on which the events are scheduled.
     * @param startTime the start time of the period when the events should take place.
     * @param endTime   the end time of the period when the events should take place.
     * @return a list of events or an empty list if no event was found with the given identifiers.
     */
    @Query("select e from Event e where e.date = :date and e.startTime between :start and :end")
    List<Event> findByTime(@Param("date") LocalDate date, @Param("start") LocalTime startTime, @Param("end") LocalTime endTime);

    /**
     * Deletes events that are attended by a specific group.
     *
     * @param group the group that attends the events.
     */
    void deleteByGroup(Group group);

    /**
     * Deletes events that are related to a specific subject.
     *
     * @param subject the subject the events are related to.
     */
    void deleteBySubject(Subject subject);
}