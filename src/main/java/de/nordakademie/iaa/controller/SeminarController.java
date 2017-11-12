package de.nordakademie.iaa.controller;

import de.nordakademie.iaa.model.Seminar;
import de.nordakademie.iaa.service.SeminarService;
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
 * REST-Controller for seminar entities.
 *
 * @author Timo Schlatter
 */
@Transactional
@RestController
@RequestMapping("/seminars")
public class SeminarController {

    private final SeminarService seminarService;
    private final SubjectService subjectService;

    @Autowired
    public SeminarController(SeminarService seminarService, SubjectService subjectService) {
        this.seminarService = seminarService;
        this.subjectService = subjectService;
    }

    /**
     * Lists all seminars.
     *
     * @return the list of all saved seminars.
     */
    @GetMapping
    public List<Seminar> listSeminars() {
        return seminarService.listSeminars();
    }

    /**
     * Saves the given seminar.
     *
     * @param seminar the seminar to save.
     * @return status OK or BAD_REQUEST (if the given seminar is already existing or saving failed).
     */
    @PostMapping
    public ResponseEntity saveSeminar(@RequestBody Seminar seminar) {
        try {
            if (seminarService.findSeminarByTitle(seminar.getTitle()) == null) {
                seminarService.saveSeminar(seminar);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        } catch (Exception ignored) {
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Updates the seminar with given id.
     *
     * @param id      identifier for seminar to update.
     * @param seminar new values for seminar.
     * @return status OK or BAD_REQUEST (if update failed).
     */
    @RequestMapping(value = "/{id}", method = PUT)
    public ResponseEntity updateSeminar(@PathVariable Long id, @RequestBody Seminar seminar) {
        try {
            if (seminarService.loadSeminar(id) != null) {
                seminarService.saveSeminar(seminar);
                return ResponseEntity.noContent().build();
            }
        } catch (Exception ignored) {
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Deletes the seminar with given id.
     *
     * @param id identifier for seminar to delete.
     * @return status OK or BAD_REQUEST (if deletion failed or no seminar was found for given id).
     */
    @RequestMapping(value = "/{id}", method = DELETE)
    public ResponseEntity deleteSeminar(@PathVariable Long id) {
        Seminar seminar = seminarService.loadSeminar(id);
        if (seminar != null) {
            subjectService.deleteSubjectByModule(seminar);
            seminarService.deleteSeminar(id);
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.badRequest().build();
    }
}