package de.nordakademie.iaa.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class Lecture extends Subject implements Serializable {

    @ManyToOne
    private Course course;

    @Basic
    private boolean isExam;

    public Lecture() {}

    public Lecture(int minChangeoverTime, Course course, boolean isExam) {
        super(minChangeoverTime);
        this.course = course;
        this.isExam = isExam;
        this.title = course.getTitle();
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
        title = course.getTitle();
    }

    public boolean isExam() {
        return isExam;
    }

    public void setExam(boolean exam) {
        isExam = exam;
    }
}