package de.nordakademie.iaa.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class Lecture extends Subject implements Serializable {

    private Course course;
    private boolean isExam;

    public Lecture() {}

    public Lecture(Course course, boolean isExam) {
        this.course = course;
        this.isExam = isExam;
        this.title = course.getTitle();
    }

    public Lecture(int minChangeoverTime, String title, Course course, boolean isExam) {
        super(minChangeoverTime, title);
        this.course = course;
        this.isExam = isExam;
    }

    @ManyToOne
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Basic
    public boolean isExam() {
        return isExam;
    }

    public void setExam(boolean exam) {
        isExam = exam;
    }

    @Override
    public String getTitle() {
        return title;
    }
}