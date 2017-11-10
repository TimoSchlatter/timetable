package de.nordakademie.iaa.service.impl;

import de.nordakademie.iaa.dao.SeminarGroupDAO;
import de.nordakademie.iaa.model.SeminarGroup;
import de.nordakademie.iaa.service.SeminarGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SeminarGroupServiceImpl implements SeminarGroupService {

    private SeminarGroupDAO seminarGroupDAO;

    @Autowired
    public SeminarGroupServiceImpl(SeminarGroupDAO seminarGroupDAO) {
        this.seminarGroupDAO = seminarGroupDAO;
    }

    @Override
    public void saveSeminarGroup(SeminarGroup seminarGroup){
        seminarGroupDAO.save(seminarGroup);
    }

    @Override
    public List<SeminarGroup> listSeminarGroups() {
        return seminarGroupDAO.findAll();
    }

    @Override
    public SeminarGroup loadSeminarGroup(Long id) {
        return seminarGroupDAO.findOne(id);
    }

    @Override
    public boolean deleteSeminarGroup(Long id) {
        SeminarGroup seminarGroup = loadSeminarGroup(id);
        if (seminarGroup == null) {
            return false;
        }
        seminarGroupDAO.delete(seminarGroup);
        return true;
    }

    @Override
    public SeminarGroup findByName(String name) {
        return seminarGroupDAO.findByName(name);
    }
}
