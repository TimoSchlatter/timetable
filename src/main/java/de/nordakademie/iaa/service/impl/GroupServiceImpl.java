package de.nordakademie.iaa.service.impl;

import de.nordakademie.iaa.dao.GroupDAO;
import de.nordakademie.iaa.model.Group;
import de.nordakademie.iaa.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private GroupDAO groupDAO;

    @Autowired
    public GroupServiceImpl(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    @Override
    public void saveGroup(Group group){
        groupDAO.save(group);
    }

    @Override
    public List<Group> listGroups() {
        return groupDAO.findAll();
    }

    @Override
    public Group loadGroup(Long id) {
        return groupDAO.findOne(id);
    }

    @Override
    public boolean deleteGroup(Long id) {
        Group group = loadGroup(id);
        if (group == null) {
            return false;
        }
        groupDAO.delete(group);
        return true;
    }
}
