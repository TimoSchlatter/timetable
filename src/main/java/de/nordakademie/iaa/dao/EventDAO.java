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
     *
     * @param date
     * @param startTime
     * @param endTime
     * @param group
     * @return
     */
    Event findByDateAndStartTimeAndEndTimeAndGroup(LocalDate date, LocalTime startTime, LocalTime endTime, Group group);

    /**
     *
     * @param room
     * @return
     */
    List<Event> findByRooms(Room room);

    /**
     *
     * @param docent
     * @return
     */
    List<Event> findByDocents(Docent docent);

    /**
     *
     * @param date
     * @param startTime
     * @param endTime
     * @return
     */
    @Query("select e from Event e where e.date = :date and e.startTime between :start and :end")
    List<Event> findByTime(@Param("date")LocalDate date, @Param("start") LocalTime startTime, @Param("end") LocalTime endTime);

    /**
     *
     * @param group
     */
    void deleteByGroup(Group group);

    /**
     *
     * @param subject
     */
    void deleteBySubject(Subject subject);

    /**
     *
     * @param room
     */
    void deleteByRooms(Room room);
}
