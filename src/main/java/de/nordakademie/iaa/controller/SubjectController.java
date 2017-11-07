package de.nordakademie.iaa.controller;


import de.nordakademie.iaa.model.Subject;
import de.nordakademie.iaa.service.SubjectService;
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
@RequestMapping("/subjects")
public class SubjectController {

    private SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * List all subjects.
     *
     * @return the list of subjects.
     */
    @GetMapping
    public List<Subject> listSubjects() {
        return subjectService.listSubjects();
    }

    /**
     * Saves the given subject.
     *
     * @param subject The subject to save.
     */
    @PostMapping
    public ResponseEntity saveSubject(@RequestBody Subject subject) {
        try {
            if (subjectService.findBySubjectTypeAndModule(subject.getSubjectType(), subject.getModule()) == null) {
                subjectService.saveSubject(subject);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        } catch (Exception ignored) {}
        return ResponseEntity.badRequest().build();
    }

    /**
     * Updates the given subject.
     *
     * @param subject The subject to update.
     */
    @RequestMapping(value = "/{id}", method = PUT)
    public ResponseEntity updateSubject(@PathVariable Long id, @RequestBody Subject subject) {
        try {
            if (subjectService.loadSubject(id) != null) {
                subjectService.saveSubject(subject);
                return ResponseEntity.ok().build();
            }
        } catch (Exception ignored) {}
        return ResponseEntity.badRequest().build();
    }

    /**
     * Deletes the subject with given id.
     *
     * @param id The id of the subject to be deleted.
     */
    @RequestMapping(value = "/{id}", method = DELETE)
    public ResponseEntity deleteSubject(@PathVariable Long id) {
        try {
            subjectService.deleteSubject(id);
            return ResponseEntity.ok(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
