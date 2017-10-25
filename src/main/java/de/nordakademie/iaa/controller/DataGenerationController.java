package de.nordakademie.iaa.controller;

import de.nordakademie.iaa.util.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DataGenerationController {

    private DataGenerator dataGenerator;

    @Autowired
    public DataGenerationController(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @RequestMapping("/generateData")
    public ResponseEntity generateData() {
        dataGenerator.createData();
        return ResponseEntity.ok(null);
    }
}
