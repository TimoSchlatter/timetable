package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Century;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DAO for Centuries
 *
 * @author Arvid Ottenberg
 */
public interface CenturyDAO extends JpaRepository<Century, Long>, BaseDAO<Century, Long> {

    /**
     * Find a Century by its name
     *
     * @param name name of the century
     * @return the century with the given name
     */
    Century findByName(String name);
}
