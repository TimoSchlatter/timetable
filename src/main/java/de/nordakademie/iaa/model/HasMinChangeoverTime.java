package de.nordakademie.iaa.model;

import java.time.Duration;

public abstract class HasMinChangeoverTime extends HasId {

	private Duration minChangeoverTime;

	public Duration getMinChangeoverTime() {
		return this.minChangeoverTime;
	}

	public void setMinChangeoverTime(Duration minChangeoverTime) {
		this.minChangeoverTime = minChangeoverTime;
	}

}