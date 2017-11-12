package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Maniple;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DAO for maniple entities.
 *
 * @author Arvid Ottenberg
 */
public interface ManipleDAO extends JpaRepository<Maniple, Long> {

    /**
     * Retrieves a maniple by its name.
     *
     * @param name the identifier.
     * @return the found maniple or {@code null} if no maniple was found with the given identifier.
     */
    Maniple findByName(String name);
}