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
     * List all docents.
     *
     * @return the list of docents.
     */
    @GetMapping
    public List<Docent> listDocents() {
        return docentService.listDocents();
    }

    /**
     * Saves the given docent.
     *
     * @param docent The docent to save.
     */
    @PostMapping
    public ResponseEntity saveDocent(@RequestBody Docent docent) {
        try {
            if (docentService.findByForenameAndSurname(docent.getForename(), docent.getSurname()) == null) {
                docentService.saveDocent(docent);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        } catch (Exception ignored) {}
        return ResponseEntity.badRequest().build();
    }

    /**
     * Updates the given docent.
     *
     * @param docent The docent to update.
     */
    @RequestMapping(value = "/{id}", method = PUT)
    public ResponseEntity updateDocent(@PathVariable Long id, @RequestBody Docent docent) {
        try {
            if (docentService.loadDocent(id) != null) {
                docentService.saveDocent(docent);
                return ResponseEntity.ok().build();
            }
        } catch (Exception ignored) {}
        return ResponseEntity.badRequest().build();
    }

    /**
     * Deletes the docent with given id.
     *
     * @param id The id of the docent to be deleted.
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
