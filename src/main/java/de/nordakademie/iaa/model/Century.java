package de.nordakademie.iaa.model;

import javax.persistence.Basic;
import javax.persistence.Entity;

@Entity
public class Century extends Group {

	private int numberOfStudents;

	public Century() {}

	public Century(String name, int numberOfStudents) {
        super(name);
		this.numberOfStudents = numberOfStudents;
    }

	public void setNumberOfStudents(int numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
	}

	@Override
	@Basic
	public int getNumberOfStudents() {
		return numberOfStudents;
	}


}