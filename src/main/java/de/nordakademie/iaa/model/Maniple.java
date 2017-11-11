package de.nordakademie.iaa.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Maniple entity.
 *
 * @author Arvid Ottenberg
 */
@Entity
public class Maniple extends Group {

    private List<Century> centuries = new ArrayList<>();

	public Maniple() {}

	public Maniple(String name) {
		super(name);
	}

	public Maniple(String name, int minChangeoverTime) {
		super(name, minChangeoverTime);
	}

	public Maniple(String name, int minChangeoverTime, List<Century> centuries) {
		super(name, minChangeoverTime);
		this.centuries = centuries;
    }

	@OneToMany(cascade = CascadeType.ALL)
	public List<Century> getCenturies() {
		return centuries;
	}

	public void setCenturies(List<Century> centuries) {
		this.centuries = centuries;
	}

	public void addCentury(Century century) {
		centuries.add(century);
	}

	public void removeCentury(Century century) {
		centuries.remove(century);
	}

	@Override
	public int calculateNumberOfStudents() {
		return !centuries.isEmpty() ? centuries.stream().mapToInt(Century::getNumberOfStudents).sum() : 0;
	}

	@Override
	public boolean hasSubGroup(Group group) {
		return centuries.contains(group);
	}

	@Override
	public String toString() {
		return "Manipel" + super.toString();
	}
}