package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Event;
import de.nordakademie.iaa.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by arvid on 19.10.17.
 */
public interface EventDAO extends JpaRepository<Event,Long>, BaseDAO<Event, Long> {

    Event findByDateAndStartTimeAndEndTimeAndGroup(LocalDate date, LocalTime startTime, LocalTime endTime, Group group);
}
