package de.nordakademie.iaa.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Maniple extends Group {

    private List<Century> centuries = new ArrayList<>();

	public Maniple() {}

	public Maniple(String name, Duration duration) {
        super(name,duration);
    }

	@Basic
	@OneToMany
	public List<Century> getCenturies() {
		return centuries;
	}

	@Override
	public int calculateNumberOfStudents() {
		if(centuries.isEmpty()) {
			return 0;
		}
		else {
			return centuries.stream().mapToInt(Century::getNumberOfStudents).sum();
		}
	}

	public void setCenturies(List<Century> centuries) {
		this.centuries = centuries;
	}

	public void addCentury(char name, int numberOfStudents, Duration duration) {
		centuries.add(new Century(this.getName() + name, numberOfStudents, duration));
	}

	public void removeCentury(Century century) {
		centuries.remove(century);
	}
}