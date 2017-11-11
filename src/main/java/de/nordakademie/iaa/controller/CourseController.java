package de.nordakademie.iaa.controller;

import de.nordakademie.iaa.model.Course;
import de.nordakademie.iaa.service.CourseService;
import de.nordakademie.iaa.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * REST-Controller for course entities.
 *
 * @author Timo Schlatter
 */
@Transactional
@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;
    private final SubjectService subjectService;

    @Autowired
    public CourseController(CourseService courseService, SubjectService subjectService) {
        this.courseService = courseService;
        this.subjectService = subjectService;
    }

    /**
     * Lists all courses.
     *
     * @return the list of all saved courses.
     */
    @GetMapping
    public List<Course> listCourses() {
        return courseService.listCourses();
    }

    /**
     * Saves the given course.
     *
     * @param course the course to save.
     * @return status OK or BAD_REQUEST (if the given course is already existing or saving failed).
     */
    @PostMapping
    public ResponseEntity saveCourse(@RequestBody Course course) {
        try {
            if (courseService.findByTitle(course.getTitle()) == null) {
                courseService.saveCourse(course);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        } catch (Exception ignored) {
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Updates the course with given id.
     *
     * @param id     identifier for course to update.
     * @param course new values for course.
     * @return status OK or BAD_REQUEST (if update failed).
     */
    @RequestMapping(value = "/{id}", method = PUT)
    public ResponseEntity updateCourse(@PathVariable Long id, @RequestBody Course course) {
        try {
            if (courseService.loadCourse(id) != null) {
                courseService.saveCourse(course);
                return ResponseEntity.noContent().build();
            }
        } catch (Exception ignored) {
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Deletes the course with given id.
     *
     * @param id identifier for course to delete.
     * @return status OK or BAD_REQUEST (if deletion failed or no course was found for given id).
     */
    @RequestMapping(value = "/{id}", method = DELETE)
    public ResponseEntity deleteCourse(@PathVariable Long id) {
        Course course = courseService.loadCourse(id);
        if (course != null) {
            subjectService.deleteSubjectByModule(course);
            courseService.deleteCourse(id);
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.badRequest().build();
    }
}