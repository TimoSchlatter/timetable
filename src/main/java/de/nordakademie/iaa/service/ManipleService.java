package de.nordakademie.iaa.service;

import de.nordakademie.iaa.model.Maniple;
import de.nordakademie.iaa.service.exception.NotEnoughChangeoverTimeProvidedException;

import java.util.List;

/**
 * Service interface for maniple entities.
 *
 * @author Timo Schlatter
 */
public interface ManipleService {

    /**
     * Stores the given maniple into the database.
     *
     * @param maniple the maniple to be saved.
     */
    void saveManiple(Maniple maniple) throws NotEnoughChangeoverTimeProvidedException;

    /**
     * Lists all maniples currently stored in the database.
     *
     * @return a list of maniple entities. If no maniple was found an empty list is returned.
     */
    List<Maniple> listManiples();

    /**
     * Retrieves a maniple by its id.
     *
     * @param id the identifier.
     * @return the found maniple or {@code null} if no maniple was found with the given id.
     */
    Maniple loadManiple(Long id);

    /**
     * Deletes the maniple with the given id.
     *
     * @param id the identifier.
     * @return <tt>true</tt> if the maniple was deleted.
     */
    boolean deleteManiple(Long id);

    /**
     * Retrieves a maniple by its name.
     *
     * @param name the identifier.
     * @return the found maniple or {@code null} if no maniple was found with the given identifier.
     */
    Maniple findManipleByName(String name);
}