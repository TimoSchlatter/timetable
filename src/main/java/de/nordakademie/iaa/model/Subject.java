package de.nordakademie.iaa.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class Subject extends HasMinChangeoverTime implements Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        if (subjectType != subject.subjectType) return false;
        return module != null ? module.equals(subject.module) : subject.module == null;
    }

    @Override
    public int hashCode() {
        int result = subjectType != null ? subjectType.hashCode() : 0;
        result = 31 * result + (module != null ? module.hashCode() : 0);
        return result;
    }
}