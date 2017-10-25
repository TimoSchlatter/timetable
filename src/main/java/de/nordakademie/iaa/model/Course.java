package de.nordakademie.iaa.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Basic;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Course extends HasId implements Serializable {

    @NaturalId
    private char field;

    @NaturalId
    private int number;

    @Basic
    private String title;

    @Basic
    private String shortTitle;

    public Course() {}

    public Course(char field, int number, String title) {
        this.field = field;
        this.number = number;
        this.title = title;
        this.shortTitle = title;
    }

    public Course(char field, int number, String title, String shortTitle) {
        this.field = field;
        this.number = number;
        this.title = title;
        this.shortTitle = shortTitle;
    }

    public char getField() {
        return field;
    }

    public void setField(char field) {
        this.field = field;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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
        if (field != course.field) return false;
        return number == course.number;
    }

    @Override
    public int hashCode() {
        int result = (int) field;
        result = 31 * result + number;
        return result;
    }
}