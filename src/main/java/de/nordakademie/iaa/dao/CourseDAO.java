package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DAO for course entities.
 *
 * @author Arvid Ottenberg
 */
public interface CourseDAO extends JpaRepository<Course, Long> {

    /**
     * Finds a course by its title.
     *
     * @param title title of the course.
     * @return the course with the given title.
     */
    Course findByTitle(String title);

    /**
     * Finds a course by its field and number.
     *
     * @param field  field of the course.
     * @param number number of the course.
     * @return the course with the given field and number.
     */
    Course findByFieldAndNumber(String field, int number);
}