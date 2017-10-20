package de.nordakademie.iaa.model;

import javax.persistence.Basic;
import javax.persistence.Entity;

@Entity
public class Seminar extends Subject {

	private String title;
	private int maxNumberOfParticipants;

	@Basic
	public int getMaxNumberOfParticipants() {
		return maxNumberOfParticipants;
	}

	@Override
	public String getTitle() {
		return null;
	}
}