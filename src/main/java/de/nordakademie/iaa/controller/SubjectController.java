package de.nordakademie.iaa.controller;

import de.nordakademie.iaa.model.Subject;
import de.nordakademie.iaa.service.EventService;
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
 * REST-Controller for subject entities.
 *
 * @author Timo Schlatter
 */

@Transactional
@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private EventService eventService;
    private SubjectService subjectService;

    @Autowired
    public SubjectController(EventService eventService, SubjectService subjectService) {
        this.eventService = eventService;
        this.subjectService = subjectService;
    }

    /**
     * Lists all subjects.
     *
     * @return the list of all saved subjects.
     */
    @GetMapping
    public List<Subject> listSubjects() {
        return subjectService.listSubjects();
    }

    /**
     * Saves the given subject.
     *
     * @param subject the subject to save.
     * @return status OK or BAD_REQUEST (if the given subject is already existing or saving failed).
     */
    @PostMapping
    public ResponseEntity saveSubject(@RequestBody Subject subject) {
        try {
            if (subjectService.findSubjectBySubjectTypeAndModule(subject.getSubjectType(), subject.getModule()) == null) {
                subjectService.saveSubject(subject);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        } catch (Exception ignored) {
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Updates the subject with given id.
     *
     * @param id      identifier for subject to update.
     * @param subject new values for subject.
     * @return status OK or BAD_REQUEST (if update failed).
     */
    @RequestMapping(value = "/{id}", method = PUT)
    public ResponseEntity updateSubject(@PathVariable Long id, @RequestBody Subject subject) {
        try {
            if (subjectService.loadSubject(id) != null) {
                subjectService.saveSubject(subject);
                return ResponseEntity.ok().build();
            }
        } catch (Exception ignored) {
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Deletes the subject with given id.
     *
     * @param id identifier for subject to delete.
     * @return status OK or BAD_REQUEST (if deletion failed or no subject was found for given id).
     */
    @RequestMapping(value = "/{id}", method = DELETE)
    public ResponseEntity deleteSubject(@PathVariable Long id) {
        Subject subject = subjectService.loadSubject(id);
        if (subject != null) {
            eventService.deleteEventsBySubject(subject);
            subjectService.deleteSubject(id);
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.badRequest().build();
    }

}
