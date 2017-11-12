package de.nordakademie.iaa.service.impl;

import de.nordakademie.iaa.dao.GroupDAO;
import de.nordakademie.iaa.model.Group;
import de.nordakademie.iaa.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for group entities.
 *
 * @author Timo Schlatter
 */
@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private final GroupDAO groupDAO;

    @Autowired
    public GroupServiceImpl(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    @Override
    public Group loadGroup(Long id) {
        return groupDAO.findOne(id);
    }
}