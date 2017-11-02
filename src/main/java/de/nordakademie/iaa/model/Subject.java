package de.nordakademie.iaa.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Subject extends HasMinChangeoverTime {

    private SubjectType subjectType;
    private Module module;

    Subject() {}

    public Subject(int minChangeoverTime, SubjectType subjectType, Module module) {
        super(minChangeoverTime);
        this.subjectType = subjectType;
        this.module = module;
    }

    @NaturalId
    public SubjectType getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
    }

    @NaturalId
    @ManyToOne
    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
}