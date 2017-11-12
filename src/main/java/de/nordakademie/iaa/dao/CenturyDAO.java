package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Century;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DAO for century entities.
 *
 * @author Arvid Ottenberg
 */
public interface CenturyDAO extends JpaRepository<Century, Long> {

    /**
     * Retrieves a century by its name.
     *
     * @param name the identifier.
     * @return the found century or {@code null} if no century was found with the given identifier.
     */
    Century findByName(String name);
}