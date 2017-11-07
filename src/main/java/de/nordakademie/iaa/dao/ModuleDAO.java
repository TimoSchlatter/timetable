package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleDAO extends JpaRepository<Module,Long>,BaseDAO<Module, Long> {

    /**
     *
     * @param title
     * @return
     */
    Module findByTitle(String title);
}
