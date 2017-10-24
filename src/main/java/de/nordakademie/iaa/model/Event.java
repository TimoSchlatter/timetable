package de.nordakademie.iaa.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;

@Entity
public class Event extends HasId implements Serializable {

	private HashSet<Docent> docents;
	private Group group;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private HashSet<Room> rooms;
	private Subject subject;

	public Event() {}

	public Event(HashSet<Docent> docents, Group group, LocalDate date, LocalTime startTime, LocalTime endTime, HashSet<Room> rooms, Subject subject) {
		this.docents = docents;
		this.group = group;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.rooms = rooms;
		this.subject = subject;
	}

	@NaturalId
	public HashSet<Docent> getDocents() {
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

	@ManyToMany
	public void setDocents(HashSet<Docent> docents) {
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
	public HashSet<Room> getRooms() {
		return rooms;
	}

	@ManyToMany
	public void setRooms(HashSet<Room> rooms) {
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Event event = (Event) o;

		if (docents != null ? !docents.equals(event.docents) : event.docents != null) return false;
		if (group != null ? !group.equals(event.group) : event.group != null) return false;
		if (date != null ? !date.equals(event.date) : event.date != null) return false;
		if (startTime != null ? !startTime.equals(event.startTime) : event.startTime != null) return false;
		if (endTime != null ? !endTime.equals(event.endTime) : event.endTime != null) return false;
		return rooms != null ? rooms.equals(event.rooms) : event.rooms == null;

	}

	@Override
	public int hashCode() {
		int result = docents != null ? docents.hashCode() : 0;
		result = 31 * result + (group != null ? group.hashCode() : 0);
		result = 31 * result + (date != null ? date.hashCode() : 0);
		result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
		result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
		result = 31 * result + (rooms != null ? rooms.hashCode() : 0);
		return result;
	}
}