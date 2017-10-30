package de.nordakademie.iaa.model;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Lecture extends HasCourse implements Serializable {

    public Lecture() {}

    public Lecture(Course course) {
        super(course);
    }

    public Lecture(int minChangeoverTime, Course course) {
        super(minChangeoverTime, course);
    }
}