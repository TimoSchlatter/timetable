package de.nordakademie.iaa.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

/**
 * Seminar entity.
 *
 * @author Arvid Ottenberg
 */
@Entity
public class Seminar extends Module implements Serializable {

    private SeminarType seminarType;

    public Seminar() {
    }

    public Seminar(String title, SeminarType seminarType) {
        super(title);
        this.seminarType = seminarType;
    }

    public Seminar(String title, String shortTitle, SeminarType seminarType) {
        super(title, shortTitle);
        this.seminarType = seminarType;
    }

    @Enumerated(EnumType.STRING)
    public SeminarType getSeminarType() {
        return seminarType;
    }

    public void setSeminarType(SeminarType seminarType) {
        this.seminarType = seminarType;
    }

    @Override
    public String toString() {
        return "Seminar " + super.toString();
    }
}