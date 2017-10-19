package de.nordakademie.iaa.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

@Entity(name = "Docent")
public class Docent extends HasMinChangeoverTime implements Serializable{

	private String email;
	private String forename;
	private String surname;
	private String phoneNumber;
	private String title;
	private Set<Course> courses;
	private boolean isPermanentlyEmployed;

	@Basic
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@NaturalId
	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}
	@NaturalId
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
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
	public Set<Course> getCourses() {
		return courses;
	}
	@OneToMany
	@Basic
	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}
	@Basic
	public boolean isPermanentlyEmployed() {
		return isPermanentlyEmployed;
	}

	public void setPermanentlyEmployed(boolean permanentlyEmployed) {
		isPermanentlyEmployed = permanentlyEmployed;
	}
}