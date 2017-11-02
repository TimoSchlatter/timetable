package de.nordakademie.iaa.controller;


import de.nordakademie.iaa.model.Exam;
import de.nordakademie.iaa.service.ExamService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Transactional
@RestController
@RequestMapping("/exams")
public class ExamController {

    private ExamService examService;

    @Autowired
    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    /**
     * List all exams.
     *
     * @return the list of exams.
     */
    @GetMapping
    public List<Exam> listExams() {
        return examService.listExams();
    }

    /**
     * Saves the given exam.
     *
     * @param exam The exam to save.
     */
    @PostMapping
    public ResponseEntity saveExam(@RequestBody Exam exam) {
        try {
            examService.saveExam(exam);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Updates the given exam.
     *
     * @param exam The exam to update.
     */
    @RequestMapping(value = "/{id}", method = PUT)
    public ResponseEntity updateExam(@PathVariable Long id, @RequestBody Exam exam) {
        try {
            if (examService.loadExam(id) != null) {
                examService.saveExam(exam);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Deletes the exam with given id.
     *
     * @param id The id of the exam to be deleted.
     */
    @RequestMapping(value = "/{id}", method = DELETE)
    public ResponseEntity deleteExam(@PathVariable Long id) {
        try {
            examService.deleteExam(id);
            return ResponseEntity.ok(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
