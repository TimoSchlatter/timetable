package de.nordakademie.iaa.service;

import de.nordakademie.iaa.dao.CourseDAO;
import de.nordakademie.iaa.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDAO courseDAO;

    @Override
    public void saveCourse(Course course) {
        courseDAO.save(course);
    }

    public List<Course> listCourses() {
        return courseDAO.findAll();
    }

    @Override
    public Course loadCourse(Long id) {
        return courseDAO.findOne(id);
    }

    @Override
    public void deleteCourse(Long id) throws EntityNotFoundException {
        Course course = loadCourse(id);
        if (course == null) {
            throw new EntityNotFoundException();
        }
        courseDAO.delete(course);
    }
}
