package de.nordakademie.iaa.controller;

import de.nordakademie.iaa.model.SeminarType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequestMapping("/seminartypes")
public class SeminarTypeController {

    @GetMapping
    public SeminarType[] listSeminarTypes() {
        return SeminarType.values();
    }
}