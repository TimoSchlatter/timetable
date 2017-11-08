package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by arvid on 19.10.17.
 */
public interface EventDAO extends JpaRepository<Event,Long>, BaseDAO<Event, Long> {

    /**
     * Find an event by its date, start time, end time and group
     *
     * @param date date on which the event is happening
     * @param startTime start time of the event
     * @param endTime end time of the event
     * @param group group that attends the event
     * @return the event that the group attends at the given time and date
     */
    Event findByDateAndStartTimeAndEndTimeAndGroup(LocalDate date, LocalTime startTime, LocalTime endTime, Group group);

    /**
     * Find events by room
     *
     * @param room the room the events are happening in
     * @return events that happen in the given room
     */
    List<Event> findByRooms(Room room);

    /**
     * Find events by docents
     *
     * @param docent the docent who is holding the event
     * @return events that are holded by the given docent
     */
    List<Event> findByDocents(Docent docent);

    /**
     * Finds events by group
     *
     * @param group the group that attends the events
     * @return the events that are attended by the given group
     */
    List<Event> findByGroup(Group group);

    /**
     * Find events that happen during a given period
     *
     * @param date the date on which the events are scheduled
     * @param startTime the start time of the period the events should happen in
     * @param endTime the end time of the period the events should happen in
     * @return events that happen during the given period
     */
    @Query("select e from Event e where e.date = :date and e.startTime between :start and :end")
    List<Event> findByTime(@Param("date")LocalDate date, @Param("start") LocalTime startTime, @Param("end") LocalTime endTime);

    /**
     * Delete events that are attended by a specific group
     *
     * @param group the group that attends the events
     */
    void deleteByGroup(Group group);

    /**
     * Delete events that are related to a specific subject
     *
     * @param subject the subject the events are related to
     */
    void deleteBySubject(Subject subject);

    /**
     * Delete events that happen in a specific room
     *
     * @param room the room the events happen in
     */
    void deleteByRooms(Room room);
}
