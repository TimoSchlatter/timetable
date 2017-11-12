package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Docent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DAO for docent entities.
 *
 * @author Arvid Ottenberg
 */
public interface DocentDAO extends JpaRepository<Docent, Long> {

    /**
     * Retrieves a docent by his forename and surname.
     *
     * @param forename the identifier.
     * @param surname  the identifier.
     * @return the found docent or {@code null} if no docent was found with the given identifier.
     */
    Docent findByForenameAndSurname(String forename, String surname);
}