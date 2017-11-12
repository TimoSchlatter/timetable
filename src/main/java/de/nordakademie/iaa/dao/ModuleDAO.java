package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DAO for module entities.
 *
 * @author Arvid Ottenberg
 */
public interface ModuleDAO extends JpaRepository<Module, Long> {

    /**
     * Retrieves a module by title.
     *
     * @param title the identifier.
     * @return the found module or {@code null} if no module was found with the given identifier.
     */
    Module findByTitle(String title);
}