package de.nordakademie.iaa.service;

import de.nordakademie.iaa.model.Cohort;
import de.nordakademie.iaa.service.exception.NotEnoughChangeoverTimeProvidedException;

import java.util.List;

/**
 * Service interface for cohort entities.
 *
 * @author Timo Schlatter
 */
public interface CohortService {

    /**
     * Stores the given cohort into the database.
     *
     * @param cohort the cohort to be saved.
     */
    void saveCohort(Cohort cohort) throws NotEnoughChangeoverTimeProvidedException;

    /**
     * Lists all cohorts currently stored in the database.
     *
     * @return a list of cohort entities. If no cohort was found an empty list is returned.
     */
    List<Cohort> listCohorts();

    /**
     * Retrieves a cohort by its id.
     *
     * @param id the identifier.
     * @return the found cohort or {@code null} if no cohort was found with the given id.
     */
    Cohort loadCohort(Long id);

    /**
     * Deletes the cohort with the given id.
     *
     * @param id the identifier.
     * @return <tt>true</tt> if the cohort was deleted.
     */
    boolean deleteCohort(Long id);

    /**
     * Retrieves a cohort by its name.
     *
     * @param name the identifier.
     * @return the found cohort or {@code null} if no cohort was found with the given identifier.
     */
    Cohort findCohortByName(String name);
}