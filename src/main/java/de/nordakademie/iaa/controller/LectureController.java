package de.nordakademie.iaa.controller;


import de.nordakademie.iaa.model.Lecture;
import de.nordakademie.iaa.service.LectureService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Transactional
@RestController
@RequestMapping("/lectures")
public class LectureController {

    private LectureService lectureService;

    @Autowired
    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    /**
     * List all lectures.
     *
     * @return the list of lectures.
     */
    @GetMapping
    public List<Lecture> listLectures() {
        return lectureService.listLectures();
    }

    /**
     * Saves the given lecture (either by creating a new one or updating an existing).
     *
     * @param lecture The lecture to save.
     */
    @PostMapping
    public ResponseEntity saveLecture(@RequestBody Lecture lecture) {
        try {
            lectureService.saveLecture(lecture);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Deletes the lecture with given id.
     *
     * @param id The id of the lecture to be deleted.
     */
    @DeleteMapping
    @RequestMapping("/{id}")
    public ResponseEntity deleteLecture(@PathVariable Long id) {
        try {
            lectureService.deleteLecture(id);
            return ResponseEntity.ok(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
