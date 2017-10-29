package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Century;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by arvid on 20.10.17.
 */
public interface CenturyDAO extends JpaRepository<Century,Long>, BaseDAO<Century, Long> {
}
