package de.nordakademie.iaa.model;

import javax.persistence.Basic;
import javax.persistence.Entity;

/**
 * Century entity.
 *
 * @author Arvid Ottenberg
 */
@Entity
public class Century extends Group {

	private int numberOfStudents;

	public Century() {}

	public Century(String name, int numberOfStudents) {
		super(name);
		this.numberOfStudents = numberOfStudents;
	}

	public Century(String name, int minChangeoverTime, int numberOfStudents) {
		super(name, minChangeoverTime);
		this.numberOfStudents = numberOfStudents;
	}

	@Basic
	public int getNumberOfStudents() {
		return numberOfStudents;
	}

	public void setNumberOfStudents(int numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
	}

	@Override
	public int calculateNumberOfStudents() {
		return getNumberOfStudents();
	}

	@Override
	public String toString() {
		return "Zenturie" + super.toString();
	}
}