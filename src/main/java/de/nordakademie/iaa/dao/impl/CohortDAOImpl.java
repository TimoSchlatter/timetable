package de.nordakademie.iaa.dao.impl;

import de.nordakademie.iaa.dao.CohortDAO;
import de.nordakademie.iaa.dao.common.DAO;
import de.nordakademie.iaa.model.Cohort;

/**
 * Created by arvid on 20.10.17.
 */
public class CohortDAOImpl extends DAO<Cohort> implements CohortDAO {

    public CohortDAOImpl() {
        setClass(Cohort.class);
    }
}
