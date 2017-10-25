package model;

import de.nordakademie.iaa.model.Course;
import de.nordakademie.iaa.model.Exam;
import de.nordakademie.iaa.model.Lecture;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class HasCourseTest {

    private final Course course = new Course('I', 151, "Test-Vorlesung", "TV");

    @Test
    public void testGetTitle() {
        Exam exam = new Exam();
        assertNull(exam.getTitle());
        exam.setCourse(course);
        assertEquals(course.getTitle(), exam.getTitle());

        exam = new Exam(30, course);
        assertEquals(course.getTitle(), exam.getTitle());

        Lecture lecture = new Lecture();
        assertNull(lecture.getTitle());
        lecture.setCourse(course);
        assertEquals(course.getTitle(), lecture.getTitle());

        lecture = new Lecture(15, course);
        assertEquals(course.getTitle(), lecture.getTitle());
    }
}
