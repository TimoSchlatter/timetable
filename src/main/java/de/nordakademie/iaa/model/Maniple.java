package de.nordakademie.iaa.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Maniple extends Group {

    private List<Century> centuries = new ArrayList<>();

    public Maniple(String name) {
        super(name);
    }

    @Basic
    @OneToMany
    public List<Century> getCenturies() {
        return centuries;
    }

    @Override
    public int getNumberOfStudents() {
        if (centuries.isEmpty()) {
            return 0;
        } else {
            return centuries.stream().mapToInt(Century::getNumberOfStudents).sum();
        }
    }

    public void addCentury(char name, int numberOfStudents) {
        centuries.add(new Century(this.getName() + name, numberOfStudents));
    }

    public void removeCentury(Century century) {
        centuries.remove(century);
    }
}