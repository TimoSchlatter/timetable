package de.nordakademie.iaa.service;

import de.nordakademie.iaa.model.SeminarGroup;

import java.util.List;

/**
 * Service interface for seminar group entities.
 *
 * @author Timo Schlatter
 */
public interface SeminarGroupService {


    /**
     * Stores the given seminar group into the database.
     *
     * @param seminarGroup the seminar group to be saved.
     */
    void saveSeminarGroup(SeminarGroup seminarGroup);

    /**
     * Lists all seminar groups currently stored in the database.
     *
     * @return a list of seminar group entities. If no seminarGroup was found an empty list is returned.
     */
    List<SeminarGroup> listSeminarGroups();

    /**
     * Retrieves a seminar group by its id.
     *
     * @param id the identifier.
     * @return the found seminar group or {@code null} if no seminar group was found with the given id.
     */
    SeminarGroup loadSeminarGroup(Long id);

    /**
     * Deletes the seminar group with the given id.
     *
     * @param id the identifier.
     * @return <tt>true</tt> if the seminar group was deleted.
     */
    boolean deleteSeminarGroup(Long id);

    /**
     * Retrieves a seminar group by its name.
     *
     * @param name the identifier.
     * @return the found seminar group or {@code null} if no seminar group was found with the given identifier.
     */
    SeminarGroup findSeminarGroupByName(String name);
}