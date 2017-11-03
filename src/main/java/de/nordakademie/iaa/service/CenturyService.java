package de.nordakademie.iaa.service;

import de.nordakademie.iaa.model.Century;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;

import java.util.List;


public interface CenturyService {

    /**
     * Stores the given century into the database.
     *
     * @param century the century to be saved.
     */
    void saveCentury(Century century);

    /**
     * List all centuries currently stored in the database.
     *
     * @return a list of Century entities. If no century was found an empty list is returned.
     */
    List<Century> listCenturies();

    /**
     * Returns the century identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    Century loadCentury(Long id);

    /**
     * Deletes the century with the given id.
     *
     * @param id the identifier.
     * @throws EntityNotFoundException if no century could be found for the given id.
     */
    void deleteCentury(Long id) throws EntityNotFoundException;

    /**
     * Retrieves a century by its name.
     * @param name the identifier.
     * @return the found century or null if no century was found with given identifier.
     */
    Century findByName(String name);
}
