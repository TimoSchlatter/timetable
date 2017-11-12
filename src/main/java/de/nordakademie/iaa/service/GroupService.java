package de.nordakademie.iaa.service;

import de.nordakademie.iaa.model.Group;

/**
 * Service interface for group entities.
 *
 * @author Timo Schlatter
 */
public interface GroupService {

    /**
     * Retrieves a group by its id.
     *
     * @param id the identifier.
     * @return the found group or {@code null} if no group was found with the given id.
     */
    Group loadGroup(Long id);
}