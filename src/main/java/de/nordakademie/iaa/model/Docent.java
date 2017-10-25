package de.nordakademie.iaa.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.HashSet;

@Entity
public class Docent extends HasMinChangeoverTime implements Serializable{

	@NaturalId
	private String forename;

	@NaturalId
	private String surname;

	@Basic
	private String email;

	@Basic
	private String phoneNumber;

	@Basic
	private String title;

	@ManyToMany
	private HashSet<Course> courses;

	@Basic
	private boolean isPermanentlyEmployed;

	public Docent() {}

	public Docent(String email, String forename, String surname, String phoneNumber, String title, boolean isPermanentlyEmployed, int minChangeoverTime, HashSet<Course> courses) {
		super(minChangeoverTime);
		this.courses = courses;
		this.email = email;
		this.forename = forename;
		this.surname = surname;
		this.phoneNumber = phoneNumber;
		this.title = title;
		this.courses = courses;
		this.isPermanentlyEmployed = isPermanentlyEmployed;
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public HashSet<Course> getCourses() {
		return courses;
	}

	public void setCourses(HashSet<Course> courses) {
		this.courses = courses;
	}

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
}