package de.nordakademie.iaa.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Basic;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Docent extends HasMinChangeoverTime implements Serializable {

    private String forename;
    private String surname;
    private String email;
    private String phoneNumber;
    private String title;
    private boolean isPermanentlyEmployed;

    public Docent() {}

    public Docent(String email, String forename, String surname, String phoneNumber, String title, boolean isPermanentlyEmployed, int minChangeoverTime) {
        super(minChangeoverTime);
        this.email = email;
        this.forename = forename;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.title = title;
        this.isPermanentlyEmployed = isPermanentlyEmployed;
    }

    @NaturalId
    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    @NaturalId(mutable = true)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    public boolean isPermanentlyEmployed() {
        return isPermanentlyEmployed;
    }

    public void setPermanentlyEmployed(boolean permanentlyEmployed) {
        isPermanentlyEmployed = permanentlyEmployed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Docent docent = (Docent) o;
        if (forename != null ? !forename.equals(docent.forename) : docent.forename != null) return false;
        return surname != null ? surname.equals(docent.surname) : docent.surname == null;
    }

    @Override
    public int hashCode() {
        int result = forename != null ? forename.hashCode() : 0;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        if(title != null) {
            return "Dozent " + title + " " + forename + " " + surname;
        }
        else {
            return "Dozent " + forename + " " + surname;
        }

    }
}