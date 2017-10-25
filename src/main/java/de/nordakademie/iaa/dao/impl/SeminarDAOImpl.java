package de.nordakademie.iaa.dao.impl;

import de.nordakademie.iaa.dao.SeminarDAO;
import de.nordakademie.iaa.dao.common.DAO;
import de.nordakademie.iaa.model.Seminar;
import org.springframework.stereotype.Repository;


/**
 * Created by arvid on 25.10.17.
 */

@Repository
public class SeminarDAOImpl extends DAO<Seminar> implements SeminarDAO {

    public SeminarDAOImpl() { setClass(Seminar.class); }
}
