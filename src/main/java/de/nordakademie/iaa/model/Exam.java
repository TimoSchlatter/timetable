package de.nordakademie.iaa.model;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Exam extends HasCourse implements Serializable {

    public Exam() {}

    public Exam(int minChangeoverTime, Course course) {
        super(minChangeoverTime, course);
    }
}