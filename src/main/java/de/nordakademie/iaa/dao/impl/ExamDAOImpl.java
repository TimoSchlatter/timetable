package de.nordakademie.iaa.dao.impl;

import de.nordakademie.iaa.dao.ExamDAO;
import de.nordakademie.iaa.dao.common.DAO;
import de.nordakademie.iaa.model.Exam;
import org.springframework.stereotype.Repository;

/**
 * Created by arvid on 25.10.17.
 */
@Repository
public class ExamDAOImpl extends DAO<Exam> implements ExamDAO {

    public ExamDAOImpl(){
        setClass(Exam.class);
    }
}
