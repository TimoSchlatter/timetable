package de.nordakademie.iaa.dao.impl;

import de.nordakademie.iaa.dao.common.DAO;
import de.nordakademie.iaa.dao.DocentDAO;
import de.nordakademie.iaa.model.Course;
import de.nordakademie.iaa.model.Docent;

import java.util.List;

/**
 * Created by arvid on 19.10.17.
 */
public class DocentDAOImpl extends DAO<Docent> implements DocentDAO {

    public DocentDAOImpl() {
        setClass(Docent.class);
    }

    @Override
    public Docent findDocentByName(String forename, String surname) {
        List<Docent> docents = entityManager.createQuery(
                "select d from Docent d where d.forename = :forename " +
                        "and d.surname = :surname")
                .setParameter("forename", forename)
                .setParameter("surname", surname)
                .getResultList();
        if(docents.isEmpty()){
            return null;
        }
        else return docents.get(0);
    }

    @Override
    public List<Docent> findDocentsByCourse(Course course) {
        List<Docent> docents = findAll();
        for(Docent docent :docents) {
            if(!docent.getCourses().contains(course)) {
                docents.remove(docent);
            }
        }
        return docents;
    }

    @Override
    public List<Docent> findDocentsByEmploymentState(boolean isPermanentlyEmployed) {
        List<Docent> docents = entityManager.createQuery(
                "select d from Docent d where d.permanentlyEmployed = :isPermanentlyEmployed ")
                .setParameter("isPermanentlyEmployed", isPermanentlyEmployed)
                .getResultList();
        return docents;
    }
}
