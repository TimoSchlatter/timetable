package de.nordakademie.iaa.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Abstract superclass for all entities.
 *
 * @author Arvid Ottenberg
 */
@MappedSuperclass
abstract class HasId {

    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}