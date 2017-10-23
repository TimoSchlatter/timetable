package de.nordakademie.iaa.model;

import javax.persistence.Basic;
import javax.persistence.Entity;

@Entity
public class Seminar extends Subject {

	private int maxNumberOfParticipants;

	public Seminar() {}

	public Seminar(int maxNumberOfParticipants, String title) {
		super(title);
		this.maxNumberOfParticipants = maxNumberOfParticipants;
	}

	@Basic
	public int getMaxNumberOfParticipants() {
		return maxNumberOfParticipants;
	}

	public void setMaxNumberOfParticipants(int maxNumberOfParticipants) {
		this.maxNumberOfParticipants = maxNumberOfParticipants;
	}

	@Override
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


}