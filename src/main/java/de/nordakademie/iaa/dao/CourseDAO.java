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
     * Retrieves a course by its title.
     *
     * @param title the identifier.
     * @return the found course or {@code null} if no course was found with the given identifier.
     */
    Course findByTitle(String title);
}