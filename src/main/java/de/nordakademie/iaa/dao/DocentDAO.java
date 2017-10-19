package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Course;
import de.nordakademie.iaa.model.Docent;

import java.util.List;

/**
 * Created by arvid on 19.10.17.
 */
public interface DocentDAO extends Operations<Docent> {
    Docent findDocentByName(String forename, String surname);
    List<Docent> findDocentsByCourse(Course course);
    List<Docent> findDocentsByEmploymentState(boolean isPermanentlyEmployed);
}
