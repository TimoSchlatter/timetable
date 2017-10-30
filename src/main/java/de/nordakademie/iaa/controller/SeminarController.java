package de.nordakademie.iaa.controller;


import de.nordakademie.iaa.model.Lecture;
import de.nordakademie.iaa.service.LectureService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seminars")
public class SeminarController {

    private LectureService seminarService;

    @Autowired
    public SeminarController(LectureService seminarService) {
        this.seminarService = seminarService;
    }

    /**
     * List all seminars.
     *
     * @return the list of seminars.
     */
    @GetMapping
    public List<Lecture> listLectures() {
        return seminarService.listLectures();
    }

    /**
     * Saves the given seminar (either by creating a new one or updating an existing).
     *
     * @param seminar The seminar to save.
     */
    @PostMapping
    public void saveLecture(@RequestBody Lecture seminar) {
        seminarService.saveLecture(seminar);
    }

    /**
     * Deletes the seminar with given id.
     *
     * @param id The id of the seminar to be deleted.
     */
    @DeleteMapping
    @RequestMapping("/{id}")
    public ResponseEntity deleteLecture(@PathVariable Long id) {
        try {
            seminarService.deleteLecture(id);
            return ResponseEntity.ok(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
