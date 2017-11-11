package de.nordakademie.iaa.controller;

import de.nordakademie.iaa.model.SeminarGroup;
import de.nordakademie.iaa.service.EventService;
import de.nordakademie.iaa.service.ManipleService;
import de.nordakademie.iaa.service.SeminarGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * REST-Controller for seminarGroup entities.
 *
 * @author Timo Schlatter
 */
@Transactional
@RestController
@RequestMapping("/seminargroups")
public class SeminarGroupController {

    private final EventService eventService;
    private final SeminarGroupService seminarGroupService;

    @Autowired
    public SeminarGroupController(EventService eventService, SeminarGroupService seminarGroupService, ManipleService manipleService) {
        this.eventService = eventService;
        this.seminarGroupService = seminarGroupService;
    }

    /**
     * Lists all seminarGroups.
     *
     * @return list of all saved seminarGroups.
     */
    @GetMapping
    public List<SeminarGroup> listSeminarGroups() {
        return seminarGroupService.listSeminarGroups();
    }

    /**
     * Saves the given seminarGroup.
     *
     * @param seminarGroup the seminarGroup to save.
     * @return status OK or BAD_REQUEST (if the given seminarGroup is already existing or saving failed).
     */
    @PostMapping
    public ResponseEntity saveSeminarGroup(@RequestBody SeminarGroup seminarGroup) {
        try {
            if (seminarGroupService.findByName(seminarGroup.getName()) == null) {
                seminarGroup.setName("Max. " + seminarGroup.getMaximumNumberOfStudents() + " Teilnehmer");
                seminarGroupService.saveSeminarGroup(seminarGroup);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        } catch (Exception ignored) {
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Updates the seminarGroup with given id.
     *
     * @param id     identifier for seminarGroup to update.
     * @param seminarGroup new values for seminarGroup.
     * @return status OK or BAD_REQUEST (if update failed).
     */
    @RequestMapping(value = "/{id}", method = PUT)
    public ResponseEntity updateSeminarGroup(@PathVariable Long id, @RequestBody SeminarGroup seminarGroup) {
        try {
            if (seminarGroupService.loadSeminarGroup(id) != null) {
                seminarGroupService.saveSeminarGroup(seminarGroup);
                return ResponseEntity.noContent().build();
            }
        } catch (Exception ignored) {
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Deletes the seminarGroup with given id.
     *
     * @param id identifier for seminarGroup to delete.
     * @return status OK or BAD_REQUEST (if deletion failed or no century was found for given id).
     */
    @RequestMapping(value = "/{id}", method = DELETE)
    public ResponseEntity deleteSeminarGroup(@PathVariable Long id) {
        SeminarGroup seminarGroup = seminarGroupService.loadSeminarGroup(id);
        if (seminarGroup != null) {
            eventService.deleteEventsByGroup(seminarGroup);
            seminarGroupService.deleteSeminarGroup(id);
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.badRequest().build();
    }
}