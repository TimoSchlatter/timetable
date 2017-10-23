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

	public Cohort(String name) {
		super(name);
	}

	@Basic
	@OneToMany
	public List<Maniple> getManiples() {
		return maniples;
	}

	@Override
	public int getNumberOfStudents() {
		if(maniples.isEmpty()) {
			return 0;
		}
		else {
			return maniples.stream().mapToInt(Maniple::getNumberOfStudents).sum();
		}
	}

	public void setManiples(List<Maniple> maniples) {
		this.maniples = maniples;
	}

	public void addManiple(char name) {
		maniples.add(new Maniple(name + this.getName()));
	}

	public void removeManiple(Maniple maniple) {
		maniples.remove(maniple);
	}
}