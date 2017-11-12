package de.nordakademie.iaa.service;

import de.nordakademie.iaa.model.Seminar;

import java.util.List;

/**
 * Service interface for seminar entities.
 *
 * @author Timo Schlatter
 */
public interface SeminarService {

    /**
     * Stores the given seminar into the database.
     *
     * @param seminar the seminar to be saved.
     */
    void saveSeminar(Seminar seminar);

    /**
     * Lists all seminars currently stored in the database.
     *
     * @return a list of seminar entities. If no seminar was found an empty list is returned.
     */
    List<Seminar> listSeminars();

    /**
     * Retrieves a seminar by its id.
     *
     * @param id the identifier.
     * @return the found seminar or {@code null} if no seminar was found with the given id.
     */
    Seminar loadSeminar(Long id);

    /**
     * Deletes the seminar with the given id.
     *
     * @param id the identifier.
     * @return <tt>true</tt> if the seminar was deleted.
     */
    boolean deleteSeminar(Long id);

    /**
     * Retrieves a seminar by its title.
     *
     * @param title the identifier.
     * @return the found seminar or {@code null} if no seminar was found with the given identifier.
     */
    Seminar findSeminarByTitle(String title);
}