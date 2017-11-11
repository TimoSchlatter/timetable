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
     * Finds a cohort by its name.
     *
     * @param name name of the cohort.
     * @return the cohort with the given name.
     */
    Cohort findByName(String name);
}
