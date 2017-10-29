package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by arvid on 19.10.17.
 */
public interface GroupDAO extends JpaRepository<Group,Long>, BaseDAO<Group, Long> {

}
