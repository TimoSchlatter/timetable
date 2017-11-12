package de.nordakademie.iaa.service.impl;

import de.nordakademie.iaa.dao.CourseDAO;
import de.nordakademie.iaa.model.Course;
import de.nordakademie.iaa.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for course entities.
 *
 * @author Timo Schlatter
 */
@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseDAO courseDAO;

    @Autowired
    public CourseServiceImpl(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    @Override
    public void saveCourse(Course course) {
        courseDAO.save(course);
    }

    @Override
    public List<Course> listCourses() {
        return courseDAO.findAll();
    }

    @Override
    public Course loadCourse(Long id) {
        return courseDAO.findOne(id);
    }

    @Override
    public boolean deleteCourse(Long id) {
        Course course = loadCourse(id);
        if (course == null) {
            return false;
        }
        courseDAO.delete(course);
        return true;
    }

    @Override
    public Course findCourseByTitle(String title) {
        return courseDAO.findByTitle(title);
    }
}