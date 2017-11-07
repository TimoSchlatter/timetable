package de.nordakademie.iaa.controller;


import de.nordakademie.iaa.model.Seminar;
import de.nordakademie.iaa.service.SeminarService;
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
@RequestMapping("/seminars")
public class SeminarController {

    private SeminarService seminarService;

    @Autowired
    public SeminarController(SeminarService seminarService) {
        this.seminarService = seminarService;
    }

    /**
     * List all seminars.
     *
     * @return the list of seminars.
     */
    @GetMapping
    public List<Seminar> listSeminars() {
        return seminarService.listSeminars();
    }

    /**
     * Saves the given seminar.
     *
     * @param seminar The seminar to save.
     */
    @PostMapping
    public ResponseEntity saveSeminar(@RequestBody Seminar seminar) {
        try {
            if (seminarService.findByTitle(seminar.getTitle()) == null) {
                seminarService.saveSeminar(seminar);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        } catch (Exception ignored) {}
        return ResponseEntity.badRequest().build();
    }

    /**
     * Updates the given seminar.
     *
     * @param seminar The seminar to update.
     */
    @RequestMapping(value = "/{id}", method = PUT)
    public ResponseEntity updateSeminar(@PathVariable Long id, @RequestBody Seminar seminar) {
        try {
            if (seminarService.loadSeminar(id) != null) {
                seminarService.saveSeminar(seminar);
                return ResponseEntity.ok().build();
            }
        } catch (Exception ignored) {}
        return ResponseEntity.badRequest().build();
    }

    /**
     * Deletes the seminar with given id.
     *
     * @param id The id of the seminar to be deleted.
     */
    @RequestMapping(value = "/{id}", method = DELETE)
    public ResponseEntity deleteSeminar(@PathVariable Long id) {
        try {
            seminarService.deleteSeminar(id);
            return ResponseEntity.ok(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
