package de.nordakademie.iaa.controller;


import de.nordakademie.iaa.model.Cohort;
import de.nordakademie.iaa.model.Maniple;
import de.nordakademie.iaa.service.CohortService;
import de.nordakademie.iaa.service.ManipleService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Transactional
@RestController
@RequestMapping("/cohorts")
public class CohortController {

    private CohortService cohortService;
    private ManipleService manipleService;

    @Autowired
    public CohortController(CohortService cohortService, ManipleService manipleService) {
        this.cohortService = cohortService;
        this.manipleService = manipleService;
    }

    /**
     * List all cohorts.
     *
     * @return the list of cohorts.
     */
    @GetMapping
    public List<Cohort> listCohorts() {
        return cohortService.listCohorts();
    }

    /**
     * Saves the given cohort (either by creating a new one or updating an existing).
     *
     * @param cohort The cohort to save.
     */
    @PostMapping
    public void saveCohort(@RequestBody Cohort cohort) {
        cohortService.saveCohort(cohort);
    }

    /**
     * Saves the given maniple (either by creating a new one or updating an existing).
     * Adds the given maniple to the referenced cohort.
     *
     * @param maniple The century to save.
     */
    @PostMapping
    @RequestMapping("/{id}/addManiple")
    public void addManiple(@PathVariable Long id, @RequestBody Maniple maniple) {
        Cohort cohort = cohortService.loadCohort(id);
        maniple.setName(maniple.getName() + cohort.getName());
        manipleService.saveManiple(maniple);
        cohort.addManiple(maniple);
    }

    /**
     * Deletes the cohort with given id.
     *
     * @param id The id of the cohort to be deleted.
     */
    @DeleteMapping
    @RequestMapping("/{id}")
    public ResponseEntity deleteCohort(@PathVariable Long id) {
        try {
            cohortService.deleteCohort(id);
            return ResponseEntity.ok(null);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
