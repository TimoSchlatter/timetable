package de.nordakademie.iaa.service;

import de.nordakademie.iaa.model.Course;

import java.util.List;

/**
 * Service interface for course entities.
 *
 * @author Timo Schlatter
 */
public interface CourseService {

    /**
     * Stores the given course into the database.
     *
     * @param course the course to be saved.
     */
    void saveCourse(Course course);

    /**
     * Lists all courses currently stored in the database.
     *
     * @return a list of course entities. If no course was found an empty list is returned.
     */
    List<Course> listCourses();

    /**
     * Retrieves a course by its id.
     *
     * @param id the identifier.
     * @return the found course or {@code null} if no course was found with the given id.
     */
    Course loadCourse(Long id);

    /**
     * Deletes the course with the given id.
     *
     * @param id the identifier.
     * @return <tt>true</tt> if the course was deleted.
     */
    boolean deleteCourse(Long id);

    /**
     * Retrieves a course by its title.
     *
     * @param title the identifier.
     * @return the found course or {@code null} if no course was found with the given identifier.
     */
    Course findCourseByTitle(String title);
}