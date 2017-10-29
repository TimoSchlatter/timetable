package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Course;
import de.nordakademie.iaa.model.Docent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by arvid on 19.10.17.
 */
public interface DocentDAO extends JpaRepository<Docent,Long>, BaseDAO<Docent, Long> {

    @Query("SELECT d FROM Docent d WHERE d.forename = :forename AND d.surname = :surname")
    Docent findDocentByName(@Param("forename")String forename, @Param("surname")String surname);

    //TODO: Kompliziertere Query noch zu implementieren
    @Query("Select d FROM Docent d")
    List<Docent> findDocentsByCourse(@Param("course")Course course);

    @Query("SELECT d FROM Docent d WHERE d.permanentlyEmployed = :permanentlyEmployed")
    List<Docent> findDocentsByEmploymentState(@Param("permanentlyEmployed")boolean isPermanentlyEmployed);
}
