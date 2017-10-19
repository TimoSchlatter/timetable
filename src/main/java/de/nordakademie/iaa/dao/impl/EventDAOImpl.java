package de.nordakademie.iaa.dao.impl;

import de.nordakademie.iaa.dao.EventDAO;
import de.nordakademie.iaa.dao.common.DAO;
import de.nordakademie.iaa.model.Event;

/**
 * Created by arvid on 19.10.17.
 */
public class EventDAOImpl extends DAO<Event> implements EventDAO {

    public EventDAOImpl() {
        setClass(Event.class);
    }
}
