package de.nordakademie.iaa.controller;


import de.nordakademie.iaa.model.Century;
import de.nordakademie.iaa.service.CenturyService;
import de.nordakademie.iaa.service.exception.NotEnoughChangeoverTimeProvidedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Transactional
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
    public List<Century> listCenturies() {
        return centuryService.listCenturies();
    }

    /**
     * Updates the given century.
     *
     * @param century The century to update.
     */
    @RequestMapping(value = "/{id}", method = PUT)
    public ResponseEntity updateCentury(@PathVariable Long id, @RequestBody Century century) {
        try {
            if (centuryService.loadCentury(id) != null) {
                centuryService.saveCentury(century);
                return ResponseEntity.ok().build();
            }
        } catch (NotEnoughChangeoverTimeProvidedException ignored) {}
        return ResponseEntity.badRequest().build();
    }
}
