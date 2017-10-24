package de.nordakademie.iaa.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Duration;

@Entity
@Table(name = "group_table")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Group extends HasMinChangeoverTime implements Serializable {

    private String name;

    public Group() {}

    public Group(String name, Duration minChangeoverTime) {
        super(minChangeoverTime);
        this.name = name;
    }

    @NaturalId
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract int calculateNumberOfStudents();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        return name != null ? name.equals(group.name) : group.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}