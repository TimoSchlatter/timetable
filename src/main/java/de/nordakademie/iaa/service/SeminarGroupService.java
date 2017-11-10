package de.nordakademie.iaa.service;

import de.nordakademie.iaa.model.SeminarGroup;

import java.util.List;

public interface SeminarGroupService {


    /**
     * Stores the given seminarGroup into the database.
     *
     * @param seminarGroup the seminarGroup to be saved.
     */
    void saveSeminarGroup(SeminarGroup seminarGroup);

    /**
     * List all seminarGroups currently stored in the database.
     *
     * @return a list of SeminarGroup entities. If no seminarGroup was found an empty list is returned.
     */
    List<SeminarGroup> listSeminarGroups();

    /**
     * Returns the seminarGroup identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    SeminarGroup loadSeminarGroup(Long id);

    /**
     * Deletes the seminarGroup with the given id.
     *
     * @param id The identifier.
     * @return .
     */
    boolean deleteSeminarGroup(Long id);

    /**
     * Retrieves a seminarGroup by its name.
     * @param name the identifier.
     * @return the found seminarGroup or null if no seminarGroup was found with given identifier.
     */
    SeminarGroup findByName(String name);
}
