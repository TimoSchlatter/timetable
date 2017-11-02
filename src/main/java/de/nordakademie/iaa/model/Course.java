package de.nordakademie.iaa.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Basic;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Course extends HasId implements Serializable {

    private String field;
    private int number;
    private String title;
    private String shortTitle;

    public Course() {}

    public Course(String field, int number, String title) {
        this.field = field;
        this.number = number;
        this.title = title;
        this.shortTitle = title;
    }

    public Course(String field, int number, String title, String shortTitle) {
        this.field = field;
        this.number = number;
        this.title = title;
        this.shortTitle = shortTitle;
    }

    @NaturalId
    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @NaturalId
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Basic
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        if (number != course.number) return false;
        return field != null ? field.equals(course.field) : course.field == null;
    }

    @Override
    public int hashCode() {
        int result = field != null ? field.hashCode() : 0;
        result = 31 * result + number;
        return result;
    }
}