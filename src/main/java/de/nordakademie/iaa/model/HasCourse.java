package de.nordakademie.iaa.model;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class HasCourse extends Subject {

    private Course course;

    HasCourse() {}

    HasCourse(int minChangeoverTime, Course course) {
        super(minChangeoverTime);
        this.course = course;
        this.title = course.getTitle();
    }

    @ManyToOne
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
        title = course.getTitle();
    }
}