package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Cohort;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DAO for cohort entities.
 *
 * @author Arvid Ottenberg
 */
public interface CohortDAO extends JpaRepository<Cohort, Long> {

    /**
     * Retrieves a cohort by its name.
     *
     * @param name the identifier.
     * @return the found cohort or {@code null} if no cohort was found with the given identifier.
     */
    Cohort findByName(String name);
}