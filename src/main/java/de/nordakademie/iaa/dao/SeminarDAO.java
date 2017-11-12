package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Seminar;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DAO for seminar entities.
 *
 * @author Arvid Ottenberg
 */
public interface SeminarDAO extends JpaRepository<Seminar, Long> {

    /**
     * Retrieves a seminar by its title.
     *
     * @param title the identifier.
     * @return the found seminar or {@code null} if no seminar was found with the given identifier.
     */
    Seminar findByTitle(String title);
}