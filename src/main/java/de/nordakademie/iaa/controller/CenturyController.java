package de.nordakademie.iaa.controller;


import de.nordakademie.iaa.model.Century;
import de.nordakademie.iaa.service.CenturyService;
import de.nordakademie.iaa.service.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/centuries")
public class CenturyController {

    private CenturyService centuryService;

    @Autowired
    public CenturyController(CenturyService centuryService) {
        this.centuryService = centuryService;
    }

    /**
     * List all centuries.
     *
     * @return the list of centuries.
     */
    @GetMapping
    public List<Century> listCenturys() {
        return centuryService.listCenturys();
    }

    /**
     * Saves the given century (either by creating a new one or updating an existing).
     *
     * @param century The century to save.
     */
    @PutMapping
    public void saveCentury(@RequestBody Century century) {
        centuryService.saveCentury(century);
    }

    /**
     * Deletes the century with given id.
     *
     * @param id The id of the century to be deleted.
     */
    @DeleteMapping
    @RequestMapping("/{id}")
    public ResponseEntity deleteCentury(@PathVariable Long id) {
        try {
            centuryService.deleteCentury(id);
            return ResponseEntity.ok(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
