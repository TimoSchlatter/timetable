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
     * Finds an event by its date, start time, end time and group.
     *
     * @param date      date on which the event is taking place.
     * @param startTime start time of the event.
     * @param endTime   end time of the event.
     * @param group     group that attends the event.
     * @return the event that the group attends at the given time and date.
     */
    Event findByDateAndStartTimeAndEndTimeAndGroup(LocalDate date, LocalTime startTime, LocalTime endTime, Group group);

    /**
     * Finds events by room.
     *
     * @param room the room where the events are taking place.
     * @return events that take place in the given room.
     */
    List<Event> findByRooms(Room room);

    /**
     * Finds events by docents.
     *
     * @param docent the docent who is holding the event.
     * @return events that are held by the given docent.
     */
    List<Event> findByDocents(Docent docent);

    /**
     * Finds events by group.
     *
     * @param group the group that attends the events.
     * @return the events that are attended by the given group.
     */
    List<Event> findByGroup(Group group);

    /**
     * Finds events that take place in a given period.
     *
     * @param date      the date on which the events are scheduled.
     * @param startTime the start time of the period when the events should take place.
     * @param endTime   the end time of the period when the events should take place.
     * @return events that take place during the given period.
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

    /**
     * Deletes events that happen in a specific room.
     *
     * @param room the room where the events take place.
     */
    void deleteByRooms(Room room);
}
