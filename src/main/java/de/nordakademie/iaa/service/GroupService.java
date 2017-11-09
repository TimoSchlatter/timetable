package de.nordakademie.iaa.service;

import de.nordakademie.iaa.model.Group;

import java.util.List;

public interface GroupService {

    /**
     * Stores the given group into the database.
     *
     * @param group the group to be saved.
     */
    void saveGroup(Group group);

    /**
     * List all centuries currently stored in the database.
     *
     * @return a list of Group entities. If no group was found an empty list is returned.
     */
    List<Group> listGroups();

    /**
     * Returns the group identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    Group loadGroup(Long id);

    /**
     * Deletes the group with the given id.
     *
     * @param id the identifier.
     * @return
     */
    boolean deleteGroup(Long id);

}
