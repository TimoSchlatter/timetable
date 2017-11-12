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
     * Retrieves a seminar group by its name.
     *
     * @param name the identifier.
     * @return the found seminar group or {@code null} if no seminar group was found with the given identifier.
     */
    SeminarGroup findByName(String name);
}