package de.nordakademie.iaa.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Century extends Group {


	private char name;

	private Maniple maniple;

	@NaturalId
	@ManyToOne
	public Maniple getManiple() {
		return maniple;
	}

	public void setManiple(Maniple maniple) {
		this.maniple = maniple;
	}
	@NaturalId
	public char getName() {
		return name;
	}

	public void setName(char name) {
		this.name = name;
	}
}