package de.nordakademie.iaa.service;

import de.nordakademie.iaa.model.Docent;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import de.nordakademie.iaa.service.exception.NotEnoughChangeoverTimeProvidedException;

import java.util.List;


public interface DocentService {

    /**
     * Stores the given docent into the database.
     *
     * @param docent the docent to be saved.
     */
    void saveDocent(Docent docent) throws NotEnoughChangeoverTimeProvidedException;

    /**
     * List all docents currently stored in the database.
     *
     * @return a list of Docent entities. If no docent was found an empty list is returned.
     */
    List<Docent> listDocents();

    /**
     * Returns the docent identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    Docent loadDocent(Long id);

    /**
     * Deletes the docent with the given id.
     *
     * @param id The identifier.
     * @throws EntityNotFoundException if no docent could be found for the given id.
     */
    void deleteDocent(Long id) throws EntityNotFoundException;

    /**
     * Retrieves a docent by its field and number.
     * @param forename the identifier.
     * @param surname the identifier.
     * @return the found docent or null if no docent was found with given identifiers.
     */
    Docent findByForenameAndSurname(String forename, String surname);
}
