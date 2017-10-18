package de.nordakademie.iaa.model;

import java.time.LocalDateTime;
import java.util.Set;

public class Event extends HasId {

	private Set<Docent> docents;
	private Group group;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Set<Room> rooms;
	private Subject subject;

	public void getTitle() {
		// TODO - implement Event.getTitle
		throw new UnsupportedOperationException();
	}

}