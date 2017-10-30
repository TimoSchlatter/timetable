package de.nordakademie.iaa.controller;


import de.nordakademie.iaa.model.Exam;
import de.nordakademie.iaa.service.ExamService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * Saves the given exam (either by creating a new one or updating an existing).
     *
     * @param exam The exam to save.
     */
    @PostMapping
    public void saveExam(@RequestBody Exam exam) {
        examService.saveExam(exam);
    }

    /**
     * Deletes the exam with given id.
     *
     * @param id The id of the exam to be deleted.
     */
    @DeleteMapping
    @RequestMapping("/{id}")
    public ResponseEntity deleteExam(@PathVariable Long id) {
        try {
            examService.deleteExam(id);
            return ResponseEntity.ok(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
