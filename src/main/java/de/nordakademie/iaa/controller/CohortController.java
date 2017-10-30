package de.nordakademie.iaa.controller;


import de.nordakademie.iaa.model.Cohort;
import de.nordakademie.iaa.service.CohortService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cohorts")
public class CohortController {

    private CohortService cohortService;

    @Autowired
    public CohortController(CohortService cohortService) {
        this.cohortService = cohortService;
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
