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

import static org.springframework.web.bind.annotation.RequestMethod.*;

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
     * Updates the given maniple.
     *
     * @param maniple The maniple to update.
     */
    @RequestMapping(value = "/{id}", method = PUT)
    public ResponseEntity updateManiple(@PathVariable Long id, @RequestBody Maniple maniple) {
        try {
            if (manipleService.loadManiple(id) != null) {
                manipleService.saveManiple(maniple);
                return ResponseEntity.ok().build();
            }
        } catch (Exception ignored) {}
        return ResponseEntity.badRequest().build();
    }
    
    /**
     * Saves the given century.
     * Adds the given century to the referenced maniple.
     *
     * @param century The century to save.
     */
    @RequestMapping(value = "/{id}/addCentury", method = POST)
    public ResponseEntity addCentury(@PathVariable Long id, @RequestBody Century century) {
        Maniple maniple = manipleService.loadManiple(id);
        if (maniple != null) {
            String newCenturyName = maniple.getName() + century.getName();
            if (centuryService.findByName(newCenturyName) == null) {
                century.setName(newCenturyName);
                centuryService.saveCentury(century);
                maniple.addCentury(century);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Deletes the maniple with the given id.
     *
     * @param manipleId The id of the cohort, to which the maniple belongs.
     * @param centuryId The id of the maniple to delete.
     */
    @RequestMapping(value = "/{manipleId}/deleteCentury/{centuryId}", method = DELETE)
    public ResponseEntity removeManiple(@PathVariable Long manipleId, @PathVariable Long centuryId) {
        Maniple maniple = manipleService.loadManiple(manipleId);
        Century century = centuryService.loadCentury(centuryId);
        if (maniple != null && century != null) {
            try {
                maniple.removeCentury(century);
                centuryService.deleteCentury(century.getId());
                return ResponseEntity.ok(null);
            } catch (Exception ignored) {}
        }
        return ResponseEntity.badRequest().build();
    }
}
