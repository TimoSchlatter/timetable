package de.nordakademie.iaa.dao.impl;

import de.nordakademie.iaa.dao.ManipleDAO;
import de.nordakademie.iaa.dao.common.DAO;
import de.nordakademie.iaa.model.Maniple;
import org.springframework.stereotype.Repository;

/**
 * Created by arvid on 20.10.17.
 */
@Repository
public class ManipleDAOImpl extends DAO<Maniple> implements ManipleDAO {

    public ManipleDAOImpl() {
        setClass(Maniple.class);
    }
}
