package de.nordakademie.iaa.dao;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.Optional;

/**
 * DAO for Centuries
 *
 * @author Arvid Ottenberg
 */
@NoRepositoryBean
public interface BaseDAO<T, ID extends Serializable> extends Repository<T, ID> {

    /**
     * Delete an object by its id
     *
     * @param id object id
     * @return
     */
    Optional<T> deleteById(ID id);
}

