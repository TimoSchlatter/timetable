package de.nordakademie.iaa.dao.impl;

import de.nordakademie.iaa.dao.CohortDAO;
import de.nordakademie.iaa.dao.common.DAO;
import de.nordakademie.iaa.model.Cohort;
import org.springframework.stereotype.Repository;

/**
 * Created by arvid on 20.10.17.
 */
@Repository
public class CohortDAOImpl extends DAO<Cohort> implements CohortDAO {

    public CohortDAOImpl() {
        setClass(Cohort.class);
    }
}
