package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Cohort;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by arvid on 20.10.17.
 */
public interface CohortDAO extends JpaRepository<Cohort,Long>, BaseDAO<Cohort, Long> {
}
