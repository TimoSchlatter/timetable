package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Course;

/**
 * Created by arvid on 19.10.17.
 */
public interface CourseDAO extends Operations<Course> {

    Course findCourseByFieldAndNumber(String field, int number);
}
