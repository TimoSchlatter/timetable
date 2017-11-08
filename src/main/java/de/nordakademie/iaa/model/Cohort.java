package de.nordakademie.iaa.model;

import javax.persistence.CascadeType;
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

    public Cohort(String name, int minChangeoverTime) {
        super(name, minChangeoverTime);
    }

    public Cohort(String name, int minChangeoverTime, List<Maniple> maniples) {
        super(name, minChangeoverTime);
        this.maniples = maniples;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public List<Maniple> getManiples() {
        return maniples;
    }

    public void setManiples(List<Maniple> maniples) {
        this.maniples = maniples;
    }

    @Override
    public int calculateNumberOfStudents() {
        if (maniples.isEmpty()) {
            return 0;
        } else {
            return maniples.stream().mapToInt(Maniple::calculateNumberOfStudents).sum();
        }
    }

    public void addManiple(Maniple maniple) {
        maniples.add(maniple);
    }

    public void removeManiple(Maniple maniple) {
        maniples.remove(maniple);
    }

    @Override
    public String toString() {
        return "Kohorte" + super.toString();
    }
}