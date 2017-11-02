package de.nordakademie.iaa.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;

@Entity
public class Subject extends HasMinChangeoverTime {

    private SubjectType subjectType;

//    private Module module;

    Subject() {}

    @NaturalId
    public SubjectType getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
    }

//    @NaturalId
//    public Module getModule() {
//        return module;
//    }
//
//    public void setModule(Module module) {
//        this.module = module;
//    }
}