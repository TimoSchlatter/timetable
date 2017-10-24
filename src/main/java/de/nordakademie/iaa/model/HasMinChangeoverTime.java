package de.nordakademie.iaa.model;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;
import java.time.Duration;

@MappedSuperclass
public abstract class HasMinChangeoverTime extends HasId {

	private Duration minChangeoverTime;

	public HasMinChangeoverTime(Duration minChangeoverTime) {
		this.minChangeoverTime = minChangeoverTime;
	}

	public HasMinChangeoverTime() {}

	@Basic
	public Duration getMinChangeoverTime() {
		return this.minChangeoverTime;
	}

	public void setMinChangeoverTime(Duration minChangeoverTime) {
		this.minChangeoverTime = minChangeoverTime;
	}

}