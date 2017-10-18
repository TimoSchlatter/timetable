package de.nordakademie.iaa.model;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Group extends HasMinChangeoverTime {

	private int numberOfStudents;

	@Basic
	public int getNumberOfStudents() {
		return numberOfStudents;
	}

	public void setNumberOfStudents(int numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
	}
}