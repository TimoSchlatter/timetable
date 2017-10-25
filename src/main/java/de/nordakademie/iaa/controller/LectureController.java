package de.nordakademie.iaa.controller;


import de.nordakademie.iaa.model.Lecture;
import de.nordakademie.iaa.service.LectureService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PutMapping
    public void saveLecture(@RequestBody Lecture lecture) {
        lectureService.saveLecture(lecture);
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
