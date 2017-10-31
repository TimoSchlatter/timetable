package de.nordakademie.iaa.controller;


import de.nordakademie.iaa.model.Docent;
import de.nordakademie.iaa.service.DocentService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Transactional
@RestController
@RequestMapping("/docents")
public class DocentController {

    private DocentService docentService;

    @Autowired
    public DocentController(DocentService docentService) {
        this.docentService = docentService;
    }

    /**
     * List all docents.
     *
     * @return the list of docents.
     */
    @GetMapping
    public List<Docent> listDocents() {
        return docentService.listDocents();
    }

    /**
     * Saves the given docent (either by creating a new one or updating an existing).
     *
     * @param docent The docent to save.
     */
    @PostMapping
    public ResponseEntity saveDocent(@RequestBody Docent docent) {
        try {
            docentService.saveDocent(docent);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Deletes the docent with given id.
     *
     * @param id The id of the docent to be deleted.
     */
    @DeleteMapping
    @RequestMapping("/{id}")
    public ResponseEntity deleteDocent(@PathVariable Long id) {
        try {
            docentService.deleteDocent(id);
            return ResponseEntity.ok(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
