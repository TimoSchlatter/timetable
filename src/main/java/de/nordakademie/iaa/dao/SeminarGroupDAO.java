package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.SeminarGroup;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DAO for seminar group entities.
 *
 * @author Arvid Ottenberg
 */
public interface SeminarGroupDAO extends JpaRepository<SeminarGroup, Long> {

    /**
     * Finds a seminar group by its name.
     *
     * @param name name of the seminar group.
     * @return the seminar group with the given name.
     */
    SeminarGroup findByName(String name);
}