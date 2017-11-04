package de.nordakademie.iaa.controller;

import de.nordakademie.iaa.model.SubjectType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequestMapping("/subjecttypes")
public class SubjectTypeController {

    @GetMapping
    public SubjectType[] listSubjectTypes() {
        return SubjectType.values();
    }
}