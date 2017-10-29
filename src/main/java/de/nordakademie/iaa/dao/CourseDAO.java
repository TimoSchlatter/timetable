package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by arvid on 19.10.17.
 */
public interface CourseDAO extends JpaRepository<Course,Long>, BaseDAO<Course, Long> {

    @Query("SELECT c FROM Course c WHERE c.field = :field AND c.number = :number")
    Course findCourseByFieldAndNumber(@Param("field")char field, @Param("number")int number);
}
