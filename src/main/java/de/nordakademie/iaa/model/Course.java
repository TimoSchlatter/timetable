package de.nordakademie.iaa.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Course extends Module implements Serializable {

    private String field;
    private int number;

    public Course() {}

    public Course(String field, int number, String title) {
        super(title);
        this.field = field;
        this.number = number;
    }

    public Course(String title, String shortTitle, String field, int number) {
        super(title, shortTitle);
        this.field = field;
        this.number = number;
    }

    @Basic
    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    @Basic
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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