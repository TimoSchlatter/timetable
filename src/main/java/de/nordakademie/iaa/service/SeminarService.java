package de.nordakademie.iaa.service;

import de.nordakademie.iaa.model.Seminar;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;

import java.util.List;


public interface SeminarService {

    /**
     * Stores the given seminar into the database.
     *
     * @param seminar the seminar to be saved.
     */
    void saveSeminar(Seminar seminar);

    /**
     * List all seminars currently stored in the database.
     *
     * @return a list of Seminar entities. If no seminar was found an empty list is
     * returned.
     */
    List<Seminar> listSeminars();

    /**
     * Returns the seminar identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    Seminar loadSeminar(Long id);

    /**
     * Deletes the seminar with the given id.
     *
     * @param id The identifier.
     * @throws EntityNotFoundException if no seminar could be found for the given id.
     */
    void deleteSeminar(Long id) throws EntityNotFoundException;

    /**
     * Retrieves a seminar by its title.
     * @param title the identifier.
     * @return the found seminar or null if no seminar was found with given identifier.
     */
    Seminar findByTitle(String title);
}
