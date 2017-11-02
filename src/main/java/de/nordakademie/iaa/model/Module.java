package de.nordakademie.iaa.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Module extends HasId {
    private String title;
    private String shortTitle;

    public Module() {}

    public Module(String title, String shortTitle) {
        this.title = title;
        this.shortTitle = shortTitle;
    }

    public Module(String title) {
        this.title = title;
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
