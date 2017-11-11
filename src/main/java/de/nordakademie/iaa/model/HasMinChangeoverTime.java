package de.nordakademie.iaa.model;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;

/**
 * Abstract superclass for all entities containing a minChangeoverTime.
 *
 * @author Arvid Ottenberg
 */
@MappedSuperclass
abstract class HasMinChangeoverTime extends HasId {

    private int minChangeoverTime;

    HasMinChangeoverTime() {
    }

    HasMinChangeoverTime(int minChangeoverTime) {
        this.minChangeoverTime = minChangeoverTime;
    }

    @Basic
    public int getMinChangeoverTime() {
        return this.minChangeoverTime;
    }

    public void setMinChangeoverTime(int minChangeoverTime) {
        this.minChangeoverTime = minChangeoverTime;
    }
}