package de.nordakademie.iaa.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Entity (name = "Room")
public class Room extends HasMinChangeoverTime implements Serializable {

	private String building;
	private int maxSeats;
	private int number;
	private RoomType roomType;

	public Room() {}

	public Room(String building, int maxSeats, int number, RoomType roomType) {
		this.building = building;
		this.maxSeats = maxSeats;
		this.number = number;
		this.roomType = roomType;
	}

	@NaturalId
	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	@Basic
	public int getMaxSeats() {
		return maxSeats;
	}

	public void setMaxSeats(int maxSeats) {
		this.maxSeats = maxSeats;
	}

	@NaturalId
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Enumerated(EnumType.STRING)
	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Room room = (Room) o;

		if (number != room.number) return false;
		return building != null ? building.equals(room.building) : room.building == null;

	}

	@Override
	public int hashCode() {
		int result = building != null ? building.hashCode() : 0;
		result = 31 * result + number;
		return result;
	}
}