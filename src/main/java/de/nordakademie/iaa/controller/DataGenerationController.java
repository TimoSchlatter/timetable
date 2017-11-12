package de.nordakademie.iaa.controller;

import de.nordakademie.iaa.data.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for dummy data generation.
 *
 * @author Timo Schlatter
 */
@Transactional
@Controller
public class DataGenerationController {

    private final DataGenerator dataGenerator;
    private boolean dataGenerated = false;

    @Autowired
    public DataGenerationController(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    /**
     * Generates dummy data.
     *
     * @return status OK or BAD_REQUEST (if an exception occurred while creating dummy data).
     */
    @RequestMapping("/generatedata")
    public ResponseEntity generateData() {
        if (!dataGenerated) {
            try {
                dataGenerator.createData();
                dataGenerated = true;
                return ResponseEntity.ok(null);
            } catch (Exception ignored) {
            }
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Displays if data was already generated.
     *
     * @return boolean if data was already generated.
     */
    @ResponseBody
    @RequestMapping("/datagenerated")
    public boolean dataGenerated() {
        return dataGenerated;
    }
}