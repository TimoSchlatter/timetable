package de.nordakademie.iaa.dao.impl;

import de.nordakademie.iaa.dao.LectureDAO;
import de.nordakademie.iaa.dao.common.DAO;
import de.nordakademie.iaa.model.Lecture;
import org.springframework.stereotype.Repository;

/**
 * Created by arvid on 25.10.17.
 */

@Repository
public class LectureDAOImpl extends DAO<Lecture> implements LectureDAO {

    public LectureDAOImpl() { setClass(Lecture.class); }
}
