package de.nordakademie.iaa.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Abstract superclass for different groups.
 *
 * @author Arvid Ottenberg
 */
@Entity
@Table(name = "group_table")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Cohort.class, name = "cohort"),
        @JsonSubTypes.Type(value = Maniple.class, name = "maniple"),
        @JsonSubTypes.Type(value = Century.class, name = "century"),
        @JsonSubTypes.Type(value = SeminarGroup.class, name = "seminargroup")})
public abstract class Group extends HasMinChangeoverTime implements Serializable {

    private String name;

    Group() {
    }

    Group(String name) {
        this.name = name;
    }

    Group(String name, int minChangeoverTime) {
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

    public boolean hasSubGroup(Group group) {
        return false;
    }

    @Override
    public String toString() {
        return " " + name + " (" + calculateNumberOfStudents() + " Studenten)";
    }

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