package de.nordakademie.iaa.service;

import de.nordakademie.iaa.model.Century;

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
    List<Century> listCenturys();

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
     * @param id The identifier.
     * @throws EntityNotFoundException if no century could be fount for the given id.
     */
    void deleteCentury(Long id) throws EntityNotFoundException;
}
