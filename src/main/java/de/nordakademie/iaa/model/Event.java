package de.nordakademie.iaa.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.OptionalInt;
import java.util.Set;

/**
 * Event entity.
 *
 * @author Arvid Ottenberg
 */
@Entity
public class Event extends HasId implements Serializable {

    private final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private Group group;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Subject subject;
    private Set<Docent> docents;
    private Set<Room> rooms;

    public Event() {
    }

    public Event(Set<Room> rooms, Set<Docent> docents, Group group, LocalDate date, LocalTime startTime, LocalTime endTime, Subject subject) {
        this.rooms = rooms;
        this.docents = docents;
        this.group = group;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.subject = subject;
    }

    @ManyToMany
    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    @ManyToMany
    public Set<Docent> getDocents() {
        return docents;
    }

    public void setDocents(Set<Docent> docents) {
        this.docents = docents;
    }

    @NaturalId(mutable = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @NaturalId(mutable = true)
    @JsonFormat(pattern = "HH:mm:ss")
    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    @NaturalId(mutable = true)
    @JsonFormat(pattern = "HH:mm:ss")
    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @NaturalId
    @ManyToOne
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @ManyToOne
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    /**
     * Removes a given room from the event's set of rooms.
     *
     * @param room the room to remove.
     * @return <tt>true</tt> if the rooms set contained the specified room.
     */
    public boolean removeRoom(Room room) {
        return rooms.remove(room);
    }

    /**
     * Removes a given docent from the event's set of docents.
     *
     * @param docent the docent to remove.
     * @return <tt>true</tt> if the docents set contained the specified docent.
     */
    public boolean removeDocent(Docent docent) {
        return docents.remove(docent);
    }

    /**
     * Calculates the maximum minChangeoverTime of all entities having a changeover time belonging to the event.
     *
     * @return minChangeoverTime for the event.
     */
    public int calculateMinChangeoverTime() {
        int maxMinChangeoverTime = group.getMinChangeoverTime();
        OptionalInt optionalDocentMax = docents.stream().mapToInt(Docent::getMinChangeoverTime).max();
        if (optionalDocentMax.isPresent()) {
            int docentMax = optionalDocentMax.getAsInt();
            maxMinChangeoverTime = (docentMax > maxMinChangeoverTime ? docentMax : maxMinChangeoverTime);
        }
        OptionalInt optionalRoomMax = rooms.stream().mapToInt(Room::getMinChangeoverTime).max();
        if (optionalRoomMax.isPresent()) {
            int roomMax = optionalRoomMax.getAsInt();
            maxMinChangeoverTime = (roomMax > maxMinChangeoverTime ? roomMax : maxMinChangeoverTime);
        }
        return maxMinChangeoverTime;
    }

    @Override
    public String toString() {
        return "Veranstaltung " + subject.toString() + " am " + date.format(dateFormatter) + " von " +
                startTime.format(timeFormatter) + "-" + endTime.format(timeFormatter) + " für " + group.toString() +
                " in " + printRooms() + " von " + printDocents();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        if (group != null ? !group.equals(event.group) : event.group != null) return false;
        if (date != null ? !date.equals(event.date) : event.date != null) return false;
        if (startTime != null ? !startTime.equals(event.startTime) : event.startTime != null) return false;
        return endTime != null ? endTime.equals(event.endTime) : event.endTime == null;
    }

    @Override
    public int hashCode() {
        int result = group != null ? group.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        return result;
    }

    private String printRooms() {
        if (rooms.size() == 1) {
            return rooms.toArray()[0].toString();
        }
        return rooms.toString();
    }

    private String printDocents() {
        if (docents.size() == 1) {
            return docents.toArray()[0].toString();
        }
        return docents.toString();
    }
}