package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleDAO extends JpaRepository<Module,Long>,BaseDAO<Module, Long> {

    /**
     *Find module by title
     *
     * @param title the title of the module
     * @return the module with the given title
     */
    Module findByTitle(String title);
}
