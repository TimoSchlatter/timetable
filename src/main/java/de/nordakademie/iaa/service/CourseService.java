package de.nordakademie.iaa.service;

import de.nordakademie.iaa.model.Course;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;

import java.util.List;


public interface CourseService {

    /**
     * Stores the given course into the database.
     *
     * @param course the course to be saved.
     */
    void saveCourse(Course course);

    /**
     * List all courses currently stored in the database.
     *
     * @return a list of Course entities. If no course was found an empty list is returned.
     */
    List<Course> listCourses();

    /**
     * Returns the course identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    Course loadCourse(Long id);

    /**
     * Deletes the course with the given id.
     *
     * @param id The identifier.
     * @throws EntityNotFoundException if no course could be found for the given id.
     */
    void deleteCourse(Long id) throws EntityNotFoundException;
}
