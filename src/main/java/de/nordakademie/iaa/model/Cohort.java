package de.nordakademie.iaa.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cohort extends Group {

	private List<Maniple> maniples = new ArrayList<>();

	public Cohort() {}

	public Cohort(String name, int duration) {
		super(name, duration);
	}

	public Cohort(String name, int minChangeoverTime, List<Maniple> maniples) {
		super(name, minChangeoverTime);
		this.maniples = maniples;
	}

	@Basic
	@OneToMany
	public List<Maniple> getManiples() {
		return maniples;
	}

	@Override
	public int calculateNumberOfStudents() {
		if(maniples.isEmpty()) {
			return 0;
		}
		else {
			return maniples.stream().mapToInt(Maniple::calculateNumberOfStudents).sum();
		}
	}

	public void setManiples(List<Maniple> maniples) {
		this.maniples = maniples;
	}

	public void addManiple(char name, int duration) {
		maniples.add(new Maniple(name + this.getName(), duration));
	}

	public void removeManiple(Maniple maniple) {
		maniples.remove(maniple);
	}
}