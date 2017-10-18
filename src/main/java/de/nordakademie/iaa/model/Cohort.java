package de.nordakademie.iaa.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;

@Entity
public class Cohort extends Group {

	private int year;

	@NaturalId
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
}