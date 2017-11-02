package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.SeminarGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeminarGroupDAO extends JpaRepository<SeminarGroup,Long>,BaseDAO<SeminarGroup, Long> {

    SeminarGroup findByName(String name);
}
