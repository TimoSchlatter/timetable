package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.SeminarGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeminarGroupDAO extends JpaRepository<SeminarGroup,Long>,BaseDAO<SeminarGroup, Long> {

    /**
     *Find a seminar group by its name
     *
     * @param name name of the seminar group
     * @return the seminar group with the given name
     */
    SeminarGroup findByName(String name);
}
