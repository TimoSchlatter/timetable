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
     * Finds a module by title.
     *
     * @param title the title of the module.
     * @return the module with the given title.
     */
    Module findByTitle(String title);
}
