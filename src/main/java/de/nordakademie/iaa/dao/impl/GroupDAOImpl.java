package de.nordakademie.iaa.dao.impl;

import de.nordakademie.iaa.dao.GroupDAO;
import de.nordakademie.iaa.dao.common.DAO;
import de.nordakademie.iaa.model.Group;
import org.springframework.stereotype.Repository;

/**
 * Created by arvid on 19.10.17.
 */
@Repository
public class GroupDAOImpl extends DAO<Group> implements GroupDAO {

    public GroupDAOImpl() {
        setClass(Group.class);
    }
}
