package de.nordakademie.iaa.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cohort extends Group {

	private List<Maniple> maniples = new ArrayList<>();

	public Cohort() {}

	public Cohort(String name, int minChangeoverTime) {
		super(name, minChangeoverTime);
	}

	public Cohort(String name, int minChangeoverTime, List<Maniple> maniples) {
		super(name, minChangeoverTime);
		this.maniples = maniples;
	}

	@OneToMany
	public List<Maniple> getManiples() {
		return maniples;
	}

	public void setManiples(List<Maniple> maniples) {
		this.maniples = maniples;
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

	public void addManiple(char name, int minChangeoverTime) {
		maniples.add(new Maniple(name + this.getName(), minChangeoverTime));
	}

	public void removeManiple(Maniple maniple) {
		maniples.remove(maniple);
	}
}