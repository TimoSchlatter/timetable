package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Docent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * DAO for docent entities.
 *
 * @author Arvid Ottenberg
 */
public interface DocentDAO extends JpaRepository<Docent, Long> {

    /**
     * Finds a docent by his/her forename and surname.
     *
     * @param forename forename of the docent.
     * @param surname  surname of the docent.
     * @return the docent with the given forename and surname.
     */
    Docent findByForenameAndSurname(String forename, String surname);

    /**
     * Finds docents by their employment state.
     *
     * @param isPermanentlyEmployed the employment state of the docent.
     * @return the docents with the given employment state.
     */
    List<Docent> findByPermanentlyEmployed(boolean isPermanentlyEmployed);
}
