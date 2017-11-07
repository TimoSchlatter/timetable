package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

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

    List<Event> findByRoom(Room room);

    List<Event> findByDocent(Docent docent);
}
