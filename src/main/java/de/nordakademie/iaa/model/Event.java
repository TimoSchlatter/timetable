package de.nordakademie.iaa.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Event extends HasId implements Serializable {

	private Set<Docent> docents;
	private Group group;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Set<Room> rooms;
	private Subject subject;
	@NaturalId
	@ManyToMany
	public Set<Docent> getDocents() {
		return docents;
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
	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	@NaturalId
	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
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