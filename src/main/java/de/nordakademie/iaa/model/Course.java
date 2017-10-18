package de.nordakademie.iaa.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Basic;
import javax.persistence.Entity;

@Entity
public class Course extends HasId {

	private char field;
	private int number;
	private String title;

	@NaturalId
	public char getField() {
		return field;
	}

	public void setField(char field) {
		this.field = field;
	}
	@NaturalId
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	@Basic
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}