package de.nordakademie.iaa.dao;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.Optional;

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

