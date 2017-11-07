package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Maniple;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by arvid on 20.10.17.
 */
public interface ManipleDAO extends JpaRepository<Maniple,Long>, BaseDAO<Maniple, Long> {

    /**
     * Find maniple by its name
     *
     * @param name name of the maniple
     * @return the maniple with the given name
     */
    Maniple findByName(String name);
}
