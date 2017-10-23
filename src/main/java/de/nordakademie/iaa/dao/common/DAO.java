package de.nordakademie.iaa.dao.common;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

/**
 * Created by arvid on 18.10.17.
 */
public abstract class DAO<T extends Serializable> implements Operations<T> {

    private Class<T> aClass;

    @PersistenceContext
    protected EntityManager entityManager;

    public void setClass(final Class<T> aClassToSet) {
        aClass = aClassToSet;
    }

    public T findOne(final long id) {
        return (T) entityManager.find(aClass, id);
    }

    public List<T> findAll() {
        return entityManager.createQuery("from " + aClass.getName()).getResultList();
    }

    public void save(final T entity) {
        entityManager.persist(entity);
    }

    public void delete(final T entity) {
        entityManager.remove(entity);
    }

    public void deleteById(final long id) {
        final T entity = findOne(id);
        delete(entity);
    }
}
