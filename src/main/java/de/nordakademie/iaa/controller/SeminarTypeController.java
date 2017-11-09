package de.nordakademie.iaa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.nordakademie.iaa.model.SeminarType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * REST-Controller for seminar types.
 *
 * @author Timo Schlatter
 */
@Transactional
@RestController
@RequestMapping("/seminartypes")
public class SeminarTypeController {

    /**
     * Lists all seminar types.
     *
     * @return the list of all seminar types.
     */
    @GetMapping
    public String listSeminarTypes() throws Exception {
        Map<SeminarType, String> seminarTypes = new HashMap<>();
        Arrays.stream(SeminarType.values()).forEach(seminarType -> seminarTypes.put(seminarType, seminarType.getTranslation()));
        return new ObjectMapper().writeValueAsString(seminarTypes);
    }
}