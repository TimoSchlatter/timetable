package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Docent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by arvid on 19.10.17.
 */
public interface DocentDAO extends JpaRepository<Docent,Long>, BaseDAO<Docent, Long> {

    Docent findByForenameAndSurname(String forename, String surname);

    List<Docent> findByPermanentlyEmployed(boolean isPermanentlyEmployed);
}
