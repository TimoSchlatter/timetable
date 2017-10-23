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
	@NaturalId
	@ManyToMany
	public Set<Docent> getDocents() {
		return docents;
	}

	@NaturalId
	@Temporal(TemporalType.DATE)
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	@NaturalId
	@Temporal(TemporalType.TIME)
	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	@NaturalId
	@Temporal(TemporalType.TIME)
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
	@Basic
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