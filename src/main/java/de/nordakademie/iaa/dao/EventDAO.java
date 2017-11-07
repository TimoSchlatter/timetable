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

    Event findByDateAndStartTimeAndEndTimeAndGroup(LocalDate date, LocalTime startTime, LocalTime endTime, Group group);

    void deleteByGroup(Group group);

    void deleteBySubject(Subject subject);

    void deleteByRooms(Room room);

    List<Event> findByRooms(Room room);

    List<Event> findByDocents(Docent docent);

    @Query("select e from Event e where e.date = :date and e.startTime between :start and :end")
    List<Event> findByTime(@Param("date")LocalDate date, @Param("start") LocalTime startTime, @Param("end") LocalTime endTime);
}
