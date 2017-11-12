package de.nordakademie.iaa.controller;

import de.nordakademie.iaa.model.Cohort;
import de.nordakademie.iaa.model.Maniple;
import de.nordakademie.iaa.service.CohortService;
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
@RequestMapping("/cohorts")
public class CohortController {

    private final EventService eventService;
    private final CohortService cohortService;
    private final ManipleService manipleService;

    @Autowired
    public CohortController(EventService eventService, CohortService cohortService, ManipleService manipleService) {
        this.eventService = eventService;
        this.cohortService = cohortService;
        this.manipleService = manipleService;
    }

    /**
     * Lists all cohorts.
     *
     * @return list of all saved cohorts.
     */
    @GetMapping
    public List<Cohort> listCohorts() {
        return cohortService.listCohorts();
    }

    /**
     * Saves the given cohort.
     *
     * @param cohort the cohort to save.
     * @return status OK or BAD_REQUEST (if the given cohort is already existing or saving failed).
     */
    @PostMapping
    public ResponseEntity saveCohort(@RequestBody Cohort cohort) {
        try {
            if (cohortService.findCohortByName(cohort.getName()) == null) {
                cohortService.saveCohort(cohort);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        } catch (Exception ignored) {
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Updates the cohort with given id.
     *
     * @param id     identifier for cohort to update.
     * @param cohort new values for cohort.
     * @return status OK or BAD_REQUEST (if update failed).
     */
    @RequestMapping(value = "/{id}", method = PUT)
    public ResponseEntity updateCohort(@PathVariable Long id, @RequestBody Cohort cohort) {
        try {
            if (cohortService.loadCohort(id) != null) {
                cohortService.saveCohort(cohort);
                return ResponseEntity.noContent().build();
            }
        } catch (Exception ignored) {
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Deletes the cohort with given id.
     *
     * @param id identifier for cohort to delete.
     * @return status OK or BAD_REQUEST (if deletion failed or no century was found for given id).
     */
    @RequestMapping(value = "/{id}", method = DELETE)
    public ResponseEntity deleteCohort(@PathVariable Long id) {
        Cohort cohort = cohortService.loadCohort(id);
        if (cohort != null) {
            cohort.getManiples().forEach(maniple -> {
                maniple.getCenturies().forEach(eventService::deleteEventsByGroup);
                eventService.deleteEventsByGroup(maniple);
            });
            eventService.deleteEventsByGroup(cohort);
            cohortService.deleteCohort(id);
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Saves the given maniple and adds the given maniple to the specified cohort.
     *
     * @param id      identifier for the cohort to which the given maniple should be added.
     * @param maniple the maniple to save.
     * @return status OK or BAD_REQUEST (if cohort is not existing, maniple is already existing or saving failed).
     */
    @RequestMapping(value = "/{id}/addManiple", method = POST)
    public ResponseEntity addManiple(@PathVariable Long id, @RequestBody Maniple maniple) {
        Cohort cohort = cohortService.loadCohort(id);
        if (cohort != null) {
            String newCenturyName = maniple.getName() + cohort.getName();
            if (manipleService.findManipleByName(newCenturyName) == null) {
                try {
                    maniple.setName(newCenturyName);
                    manipleService.saveManiple(maniple);
                    cohort.addManiple(maniple);
                    return ResponseEntity.status(HttpStatus.CREATED).build();
                } catch (NotEnoughChangeoverTimeProvidedException ignored) {
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Removes the given maniple from the specified cohort and deletes the given maniple.
     *
     * @param cohortId  identifier for the cohort from which the given maniple should be removed.
     * @param manipleId identifier for the maniple which should be removed and deleted.
     * @return status OK or BAD_REQUEST (if cohort or maniple is not existing).
     */
    @RequestMapping(value = "/{cohortId}/deleteManiple/{manipleId}", method = DELETE)
    public ResponseEntity removeManiple(@PathVariable Long cohortId, @PathVariable Long manipleId) {
        Cohort cohort = cohortService.loadCohort(cohortId);
        Maniple maniple = manipleService.loadManiple(manipleId);
        if (cohort != null && maniple != null) {
            cohort.removeManiple(maniple);
            maniple.getCenturies().forEach(eventService::deleteEventsByGroup);
            eventService.deleteEventsByGroup(maniple);
            manipleService.deleteManiple(maniple.getId());
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.badRequest().build();
    }
}