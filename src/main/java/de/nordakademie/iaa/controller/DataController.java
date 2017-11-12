package de.nordakademie.iaa.controller;

import de.nordakademie.iaa.data.DatabaseResetter;
import de.nordakademie.iaa.data.DummyDataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Controller for dummy data generation.
 *
 * @author Timo Schlatter
 */
@Transactional
@Controller
@RequestMapping("data")
public class DataController {

    private final DummyDataGenerator dummyDataGenerator;
    private final DatabaseResetter databaseResetter;

    @Autowired
    public DataController(DummyDataGenerator dummyDataGenerator, DatabaseResetter databaseResetter) {
        this.dummyDataGenerator = dummyDataGenerator;
        this.databaseResetter = databaseResetter;
    }

    /**
     * Generates dummy data.
     *
     * @return status CREATED or BAD_REQUEST (if an exception occurred while creating dummy data).
     */
    @RequestMapping(value = "/generate", method = GET)
    public ResponseEntity generateData() {
        if (databaseResetter.isDatabaseEmpty()) {
            try {
                dummyDataGenerator.createData();
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } catch (Exception ignored) {
            }
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Removes all persisted entities from the database.
     *
     * @return status OK.
     */
    @RequestMapping(value = "/clear", method = GET)
    public ResponseEntity clearDatabase() {
        databaseResetter.clearDatabase();
        return ResponseEntity.ok(null);
    }

    /**
     * Displays if database has no entities in it.
     *
     * @return <tt>true</tt> if database is empty.
     */
    @ResponseBody
    @RequestMapping(value = "/isempty", method = GET)
    public boolean databaseEmpty() {
        return databaseResetter.isDatabaseEmpty();
    }
}