package de.nordakademie.iaa.service;

import de.nordakademie.iaa.model.Century;
import de.nordakademie.iaa.service.exception.NotEnoughChangeoverTimeProvidedException;

import java.util.List;

/**
 * Service interface for century entities.
 *
 * @author Timo Schlatter
 */
public interface CenturyService {

    /**
     * Stores the given century into the database.
     *
     * @param century the century to be saved.
     */
    void saveCentury(Century century) throws NotEnoughChangeoverTimeProvidedException;

    /**
     * Lists all centuries currently stored in the database.
     *
     * @return a list of century entities. If no century was found an empty list is returned.
     */
    List<Century> listCenturies();

    /**
     * Retrieves a century by its id.
     *
     * @param id the identifier.
     * @return the found century or {@code null} if no century was found with the given id.
     */
    Century loadCentury(Long id);

    /**
     * Deletes the century with the given id.
     *
     * @param id the identifier.
     * @return <tt>true</tt> if the century was deleted.
     */
    boolean deleteCentury(Long id);

    /**
     * Retrieves a century by its name.
     *
     * @param name the identifier.
     * @return the found century or {@code null} if no century was found with the given identifier.
     */
    Century findCenturyByName(String name);
}