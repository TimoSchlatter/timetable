package de.nordakademie.iaa.model;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class HasMinChangeoverTime extends HasId {

	private int minChangeoverTime;

	public HasMinChangeoverTime(int minChangeoverTime) {
		this.minChangeoverTime = minChangeoverTime;
	}

	public HasMinChangeoverTime() {}

	@Basic
	public int getMinChangeoverTime() {
		return this.minChangeoverTime;
	}

	public void setMinChangeoverTime(int minChangeoverTime) {
		this.minChangeoverTime = minChangeoverTime;
	}

}