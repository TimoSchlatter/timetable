package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Seminar;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by arvid on 25.10.17.
 */
public interface SeminarDAO extends JpaRepository<Seminar,Long>,BaseDAO<Seminar, Long> {

    /**
     *
     * @param title
     * @return
     */
    Seminar findByTitle(String title);
}
