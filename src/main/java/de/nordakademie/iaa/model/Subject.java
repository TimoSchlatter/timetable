package de.nordakademie.iaa.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Subject extends HasMinChangeoverTime {

	public abstract String getTitle();

}