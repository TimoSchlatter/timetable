package de.nordakademie.iaa.controller;

import de.nordakademie.iaa.util.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

@Transactional
@Controller
public class DataGenerationController {

    private DataGenerator dataGenerator;
    private boolean dataGenerated = false;

    @Autowired
    public DataGenerationController(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @RequestMapping("/generateData")
    public ResponseEntity generateData() {
        if (!dataGenerated) {
            dataGenerator.createData();
            dataGenerated = true;
        }
        return ResponseEntity.ok(null);
    }
}
