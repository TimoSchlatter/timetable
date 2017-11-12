package de.nordakademie.iaa.service;

import de.nordakademie.iaa.model.Docent;
import de.nordakademie.iaa.service.exception.NotEnoughChangeoverTimeProvidedException;

import java.util.List;

/**
 * Service interface for docent entities.
 *
 * @author Timo Schlatter
 */
public interface DocentService {

    /**
     * Stores the given docent into the database.
     *
     * @param docent the docent to be saved.
     */
    void saveDocent(Docent docent) throws NotEnoughChangeoverTimeProvidedException;

    /**
     * Lists all docents currently stored in the database.
     *
     * @return a list of docent entities. If no docent was found an empty list is returned.
     */
    List<Docent> listDocents();

    /**
     * Retrieves a docent by its id.
     *
     * @param id the identifier.
     * @return the found docent or {@code null} if no docent was found with the given id.
     */
    Docent loadDocent(Long id);

    /**
     * Deletes the docent with the given id.
     *
     * @param id the identifier.
     * @return <tt>true</tt> if the docent was deleted.
     */
    boolean deleteDocent(Long id);

    /**
     * Retrieves a docent by his forename and surname.
     *
     * @param forename the identifier.
     * @param surname  the identifier.
     * @return the found docent or {@code null} if no docent was found with the given identifier.
     */
    Docent findDocentByForenameAndSurname(String forename, String surname);
}