package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Docent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * DAO for Docents
 *
 * @author Arvid Ottenberg
 */
public interface DocentDAO extends JpaRepository<Docent,Long>, BaseDAO<Docent, Long> {

    /**
     * Find a docent by his/her forename and surname
     *
     * @param forename forname of the docent
     * @param surname surname of the docent
     * @return
     */
    Docent findByForenameAndSurname(String forename, String surname);

    /**
     * Find docents by their employment state
     *
     * @param isPermanentlyEmployed the employment state of the docent
     * @return the docents with the given employment state
     */
    List<Docent> findByPermanentlyEmployed(boolean isPermanentlyEmployed);
}
