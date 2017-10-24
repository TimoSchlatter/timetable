package de.nordakademie.iaa.service;

import de.nordakademie.iaa.model.Cohort;

import java.util.List;


public interface CohortService {

    /**
     * Stores the given cohort into the database.
     *
     * @param cohort the cohort to be saved.
     */
    void saveCohort(Cohort cohort);

    /**
     * List all cohorts currently stored in the database.
     *
     * @return a list of Cohort entities. If no cohort was found an empty list is returned.
     */
    List<Cohort> listCohorts();

    /**
     * Returns the cohort identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    Cohort loadCohort(Long id);

    /**
     * Deletes the cohort with the given id.
     *
     * @param id The identifier.
     * @throws EntityNotFoundException if no cohort could be fount for the given id.
     */
    void deleteCohort(Long id) throws EntityNotFoundException;
}
