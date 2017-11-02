package de.nordakademie.iaa.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Module extends HasId {
    private String title;
    private String shortTitle;

    public Module() {}

    public Module(String title) {
        this.title = title;
        this.shortTitle = title;
    }

    public Module(String title, String shortTitle) {
        this.title = title;
        this.shortTitle = shortTitle;
    }

    @NaturalId
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }
}
