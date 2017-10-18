package de.nordakademie.iaa.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Maniple extends Group {

	private Cohort cohort;
	private char field;

	@NaturalId
	@ManyToOne
	public Cohort getCohort() {
		return cohort;
	}

	public void setCohort(Cohort cohort) {
		this.cohort = cohort;
	}
	@NaturalId
	public char getField() {
		return field;
	}

	public void setField(char field) {
		this.field = field;
	}
}