package de.nordakademie.iaa.controller;


import de.nordakademie.iaa.model.Maniple;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import de.nordakademie.iaa.service.ManipleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maniples")
public class ManipleController {

    private ManipleService manipleService;

    @Autowired
    public ManipleController(ManipleService manipleService) {
        this.manipleService = manipleService;
    }

    /**
     * List all maniples.
     *
     * @return the list of maniples.
     */
    @GetMapping
    public List<Maniple> listManiples() {
        return manipleService.listManiples();
    }

    /**
     * Saves the given maniple (either by creating a new one or updating an existing).
     *
     * @param maniple The maniple to save.
     */
    @PostMapping
    public void saveManiple(@RequestBody Maniple maniple) {
        manipleService.saveManiple(maniple);
    }

    /**
     * Deletes the maniple with given id.
     *
     * @param id The id of the maniple to be deleted.
     */
    @DeleteMapping
    @RequestMapping("/{id}")
    public ResponseEntity deleteManiple(@PathVariable Long id) {
        try {
            manipleService.deleteManiple(id);
            return ResponseEntity.ok(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
