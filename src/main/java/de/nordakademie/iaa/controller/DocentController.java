package de.nordakademie.iaa.controller;

import de.nordakademie.iaa.model.Docent;
import de.nordakademie.iaa.model.Event;
import de.nordakademie.iaa.service.DocentService;
import de.nordakademie.iaa.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * REST-Controller for docent entities.
 *
 * @author Timo Schlatter
 */

@Transactional
@RestController
@RequestMapping("/docents")
public class DocentController {

    private DocentService docentService;
    private EventService eventService;

    @Autowired
    public DocentController(DocentService docentService, EventService eventService) {
        this.docentService = docentService;
        this.eventService = eventService;
    }

    /**
     * Lists all docents.
     *
     * @return the list of all saved docents.
     */
    @GetMapping
    public List<Docent> listDocents() {
        return docentService.listDocents();
    }

    /**
     * Saves the given docent.
     *
     * @param docent the docent to save.
     * @return status OK or BAD_REQUEST (if the given docent is already existing or saving failed).
     */
    @PostMapping
    public ResponseEntity saveDocent(@RequestBody Docent docent) {
        try {
            if (docentService.findByForenameAndSurname(docent.getForename(), docent.getSurname()) == null) {
                docentService.saveDocent(docent);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        } catch (Exception ignored) {
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Updates the docent with given id.
     *
     * @param id     identifier for docent to update.
     * @param docent new values for docent.
     * @return status OK or BAD_REQUEST (if update failed).
     */
    @RequestMapping(value = "/{id}", method = PUT)
    public ResponseEntity updateDocent(@PathVariable Long id, @RequestBody Docent docent) {
        try {
            if (docentService.loadDocent(id) != null) {
                docentService.saveDocent(docent);
                return ResponseEntity.ok().build();
            }
        } catch (Exception ignored) {
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Deletes the docent with given id.
     *
     * @param id identifier for docent to delete.
     * @return status OK or BAD_REQUEST (if deletion failed or no docent was found for given id).
     */
    @RequestMapping(value = "/{id}", method = DELETE)
    public ResponseEntity deleteDocent(@PathVariable Long id) {
        Docent docent = docentService.loadDocent(id);
        if (docent != null) {
            List<Event> eventsWithDocent = eventService.findEventsByDocent(docent);
            eventsWithDocent.forEach(event -> {
                if (event.getDocents().size() == 1) {
                    eventService.deleteEvent(event.getId());
                } else {
                    event.removeDocent(docent);
                }
            });
            docentService.deleteDocent(id);
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.badRequest().build();
    }

}
