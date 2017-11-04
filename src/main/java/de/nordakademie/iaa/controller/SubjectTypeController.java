package de.nordakademie.iaa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.nordakademie.iaa.model.SubjectType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Transactional
@RestController
@RequestMapping("/subjecttypes")
public class SubjectTypeController {

    @GetMapping
    public String listSubjectTypes() throws Exception {
        Map<SubjectType, String> subjectTypes = new HashMap<>();
        Arrays.stream(SubjectType.values()).forEach(subjectType -> subjectTypes.put(subjectType, subjectType.getTranslation()));
        return new ObjectMapper().writeValueAsString(subjectTypes);
    }
}