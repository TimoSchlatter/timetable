package de.nordakademie.iaa.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
public class Event extends HasId implements Serializable {

	private Set<Docent> docents;
	private Group group;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private Set<Room> rooms;
	private Subject subject;

	public Event() {}

	public Event(Set<Docent> docents, Group group, LocalDate date, LocalTime startTime, LocalTime endTime, Set<Room> rooms, Subject subject) {
		this.docents = docents;
		this.group = group;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.rooms = rooms;
		this.subject = subject;
	}

	@NaturalId
	@ManyToMany
	public Set<Docent> getDocents() {
		return docents;
	}

	@NaturalId
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	@NaturalId
	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	@NaturalId
	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public void setDocents(Set<Docent> docents) {
		this.docents = docents;
	}

	@NaturalId
	@ManyToOne
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@NaturalId
	@ManyToMany
	public Set<Room> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}

	@ManyToOne
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public void getTitle() {
		// TODO - implement Event.getTitle
		throw new UnsupportedOperationException();
	}

}