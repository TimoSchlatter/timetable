package de.nordakademie.iaa.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Group extends HasMinChangeoverTime implements Serializable {

	private String name;

	public Group() {}

	public Group(String name) {
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NaturalId
	public String getName() {
		return name;
	}

	public abstract int getNumberOfStudents();
}