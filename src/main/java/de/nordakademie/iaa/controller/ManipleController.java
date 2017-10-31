package de.nordakademie.iaa.controller;


import de.nordakademie.iaa.model.Century;
import de.nordakademie.iaa.model.Maniple;
import de.nordakademie.iaa.service.CenturyService;
import de.nordakademie.iaa.service.ManipleService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Transactional
@RestController
@RequestMapping("/maniples")
public class ManipleController {

    private ManipleService manipleService;
    private CenturyService centuryService;

    @Autowired
    public ManipleController(ManipleService manipleService, CenturyService centuryService) {
        this.manipleService = manipleService;
        this.centuryService = centuryService;
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

    /**
     * Saves the given century (either by creating a new one or updating an existing).
     * Adds the given century to the referenced maniple.
     *
     * @param century The century to save.
     */
    @PostMapping
    @RequestMapping("/{id}/addCentury")
    public ResponseEntity addCentury(@PathVariable Long id, @RequestBody Century century) {
        Maniple maniple = manipleService.loadManiple(id);
        if (maniple != null) {
            century.setName(maniple.getName() + century.getName());
            centuryService.saveCentury(century);
            maniple.addCentury(century);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.notFound().build();
    }

}
