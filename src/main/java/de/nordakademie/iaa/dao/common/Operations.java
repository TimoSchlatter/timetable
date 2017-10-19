package de.nordakademie.iaa.dao.common;

import java.io.Serializable;
import java.util.List;

/**
 * Created by arvid on 19.10.17.
 */
public interface Operations <T extends Serializable> {

    public T findOne( final long id );

    public List< T > findAll();

    public void save( final T entity );

    public void delete( final T entity );

    public void deleteById( final long id );

}
