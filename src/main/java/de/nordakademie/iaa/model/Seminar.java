package de.nordakademie.iaa.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Seminar extends Subject implements Serializable {

    private int maxNumberOfParticipants;

    public Seminar() {}

    public Seminar(String title, int maxNumberOfParticipants) {
        super(title);
        this.maxNumberOfParticipants = maxNumberOfParticipants;
    }

    public Seminar(int minChangeoverTime, String title, int maxNumberOfParticipants) {
        super(minChangeoverTime, title);
        this.maxNumberOfParticipants = maxNumberOfParticipants;
    }

    @Basic
    public int getMaxNumberOfParticipants() {
        return maxNumberOfParticipants;
    }

    public void setMaxNumberOfParticipants(int maxNumberOfParticipants) {
        this.maxNumberOfParticipants = maxNumberOfParticipants;
    }
}