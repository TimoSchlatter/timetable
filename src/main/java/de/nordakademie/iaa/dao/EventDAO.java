package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by arvid on 19.10.17.
 */
public interface EventDAO extends JpaRepository<Event,Long>, BaseDAO<Event, Long> {

}
