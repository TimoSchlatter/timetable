package de.nordakademie.iaa.model;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Seminar extends Module implements Serializable {

    private SeminarType seminarType;

    public Seminar() {}

    public Seminar(String title, String shortTitle, SeminarType seminarType) {
        super(title, shortTitle);
        this.seminarType = seminarType;
    }

    public Seminar(String title, SeminarType seminarType) {
        super(title, title);
        this.seminarType = seminarType;
    }

    public SeminarType getSeminarType() {
        return seminarType;
    }

    public void setSeminarType(SeminarType seminarType) {
        this.seminarType = seminarType;
    }
}