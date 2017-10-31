package de.nordakademie.iaa.controller;


import de.nordakademie.iaa.model.Course;
import de.nordakademie.iaa.service.CourseService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Transactional
@RestController
@RequestMapping("/courses")
public class CourseController {

    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * List all courses.
     *
     * @return the list of courses.
     */
    @GetMapping
    public List<Course> listCourses() {
        return courseService.listCourses();
    }

    /**
     * Saves the given course (either by creating a new one or updating an existing).
     *
     * @param course The course to save.
     */
    @PostMapping
    public void saveCourse(@RequestBody Course course) {
        courseService.saveCourse(course);
    }

    /**
     * Deletes the course with given id.
     *
     * @param id The id of the course to be deleted.
     */
    @DeleteMapping
    @RequestMapping("/{id}")
    public ResponseEntity deleteCourse(@PathVariable Long id) {
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.ok(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
