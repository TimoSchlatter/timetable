package de.nordakademie.iaa.dao.impl;

import de.nordakademie.iaa.dao.ManipleDAO;
import de.nordakademie.iaa.dao.common.DAO;
import de.nordakademie.iaa.model.Maniple;

/**
 * Created by arvid on 20.10.17.
 */
public class ManipleDAOImpl extends DAO<Maniple> implements ManipleDAO {

    public ManipleDAOImpl() {
        setClass(Maniple.class);
    }
}
