package de.nordakademie.iaa.model;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
abstract class HasCourse extends Subject {

    private Course course;

    HasCourse() {}

    HasCourse(Course course) {
        super(course.getTitle());
        this.course = course;
    }

    HasCourse(int minChangeoverTime, Course course) {
        super(minChangeoverTime, course.getTitle());
        this.course = course;
    }

    @ManyToOne
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}