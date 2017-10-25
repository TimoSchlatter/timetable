package de.nordakademie.iaa.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Subject extends HasMinChangeoverTime {

    String title;

    public Subject() {}

    public Subject(String title) {
        this.title = title;
    }

    public Subject(int minChangeoverTime) {
        super(minChangeoverTime);
    }

    public Subject(int minChangeoverTime, String title) {
        super(minChangeoverTime);
        this.title = title;
    }

    @NaturalId
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject subject = (Subject) o;

        return title != null ? title.equals(subject.title) : subject.title == null;
    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }
}