package de.nordakademie.iaa.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Subject extends HasMinChangeoverTime {

	protected String title;

	public Subject() {}

	public Subject(String title) {
		this.title = title;
	}

	@NaturalId
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}