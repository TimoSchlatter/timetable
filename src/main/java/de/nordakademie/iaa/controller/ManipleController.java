package de.nordakademie.iaa.controller;

import de.nordakademie.iaa.model.Century;
import de.nordakademie.iaa.model.Maniple;
import de.nordakademie.iaa.service.CenturyService;
import de.nordakademie.iaa.service.EventService;
import de.nordakademie.iaa.service.ManipleService;
import de.nordakademie.iaa.service.exception.NotEnoughChangeoverTimeProvidedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * REST-Controller for cohort entities.
 *
 * @author Timo Schlatter
 */
@Transactional
@RestController
@RequestMapping("/maniples")
public class ManipleController {

    private EventService eventService;
    private ManipleService manipleService;
    private CenturyService centuryService;

    @Autowired
    public ManipleController(EventService eventService, ManipleService manipleService, CenturyService centuryService) {
        this.eventService = eventService;
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
     * Updates the maniple with given id.
     *
     * @param id      identifier for maniple to update.
     * @param maniple new values for maniple.
     * @return status OK or BAD_REQUEST (if update failed).
     */
    @RequestMapping(value = "/{id}", method = PUT)
    public ResponseEntity updateManiple(@PathVariable Long id, @RequestBody Maniple maniple) {
        try {
            if (manipleService.loadManiple(id) != null) {
                manipleService.saveManiple(maniple);
                return ResponseEntity.noContent().build();
            }
        } catch (Exception ignored) {
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Saves the given century and adds the given century to the specified maniple.
     *
     * @param id      identifier for the maniple to which the given century should be added.
     * @param century the century to save.
     * @return status OK or BAD_REQUEST (if maniple is not existing, century is already existing or saving failed).
     */
    @RequestMapping(value = "/{id}/addCentury", method = POST)
    public ResponseEntity addCentury(@PathVariable Long id, @RequestBody Century century) {
        Maniple maniple = manipleService.loadManiple(id);
        if (maniple != null) {
            String newCenturyName = maniple.getName() + century.getName();
            if (centuryService.findByName(newCenturyName) == null) {
                century.setName(newCenturyName);
                try {
                    centuryService.saveCentury(century);
                    maniple.addCentury(century);
                    return ResponseEntity.status(HttpStatus.CREATED).build();
                } catch (NotEnoughChangeoverTimeProvidedException ignored) {
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Removes the given century from the specified maniple and deletes the given century.
     *
     * @param manipleId identifier for the maniple from which the given century should be removed.
     * @param centuryId identifier for the century which should be removed and deleted.
     * @return status OK or BAD_REQUEST (if maniple or century is not existing).
     */
    @RequestMapping(value = "/{manipleId}/deleteCentury/{centuryId}", method = DELETE)
    public ResponseEntity removeCentury(@PathVariable Long manipleId, @PathVariable Long centuryId) {
        Maniple maniple = manipleService.loadManiple(manipleId);
        Century century = centuryService.loadCentury(centuryId);
        if (maniple != null && century != null) {
            maniple.removeCentury(century);
            eventService.deleteEventsByGroup(century);
            centuryService.deleteCentury(century.getId());
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.badRequest().build();
    }
}
