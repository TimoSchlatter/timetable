package de.nordakademie.iaa.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import java.time.Duration;

@Entity
public class Century extends Group {

	private int numberOfStudents;

	public Century() {}

	public Century(String name, int numberOfStudents, Duration minChangeoverTime) {
        super(name, minChangeoverTime);
		this.numberOfStudents = numberOfStudents;
    }

	public void setNumberOfStudents(int numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
	}

	@Basic
	public int getNumberOfStudents() {
		return numberOfStudents;
	}

	@Override
	public int calculateNumberOfStudents() {
		return getNumberOfStudents();
	}
}