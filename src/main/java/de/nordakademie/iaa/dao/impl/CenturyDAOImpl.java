package de.nordakademie.iaa.dao.impl;

import de.nordakademie.iaa.dao.CenturyDAO;
import de.nordakademie.iaa.dao.common.DAO;
import de.nordakademie.iaa.model.Century;

/**
 * Created by arvid on 20.10.17.
 */
public class CenturyDAOImpl extends DAO<Century> implements CenturyDAO{

    public CenturyDAOImpl() {
        setClass(Century.class);
    }

}
