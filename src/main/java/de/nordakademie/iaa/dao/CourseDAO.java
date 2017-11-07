package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by arvid on 19.10.17.
 */
public interface CourseDAO extends JpaRepository<Course,Long>, BaseDAO<Course, Long> {

    /**
     *
     * @param title
     * @return
     */
    Course findByTitle(String title);

    /**
     *
     * @param field
     * @param number
     * @return
     */
    Course findByFieldAndNumber(String field, int number);
}
