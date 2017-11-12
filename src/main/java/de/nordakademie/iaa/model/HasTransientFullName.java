package de.nordakademie.iaa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Abstract superclass for all entities containing a {@code @Transient} fullName.
 *
 * @author Arvid Ottenberg
 */
@MappedSuperclass
abstract class HasTransientFullName extends HasMinChangeoverTime {

    @Transient
    @JsonIgnore
    private String fullName;

    HasTransientFullName() {
    }

    HasTransientFullName(int minChangeoverTime) {
        super(minChangeoverTime);
    }

    @JsonProperty(value = "fullName")
    public abstract String getFullName();

    @JsonIgnore
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}