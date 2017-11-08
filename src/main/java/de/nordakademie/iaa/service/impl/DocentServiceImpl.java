package de.nordakademie.iaa.service.impl;

import de.nordakademie.iaa.dao.DocentDAO;
import de.nordakademie.iaa.model.Docent;
import de.nordakademie.iaa.service.DocentService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import de.nordakademie.iaa.service.exception.NotEnoughChangeoverTimeProvidedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DocentServiceImpl implements DocentService {

    private DocentDAO docentDAO;

    @Autowired
    public DocentServiceImpl(DocentDAO docentDAO) {
        this.docentDAO = docentDAO;
    }

    @Override
    public void saveDocent(Docent docent) throws NotEnoughChangeoverTimeProvidedException {
        if (docent.getMinChangeoverTime() < 15) {
            throw new NotEnoughChangeoverTimeProvidedException();
        }
        docentDAO.save(docent);
    }

    @Override
    public List<Docent> listDocents() {
        return docentDAO.findAll();
    }

    @Override
    public Docent loadDocent(Long id) {
        return docentDAO.findOne(id);
    }

    @Override
    public void deleteDocent(Long id) throws EntityNotFoundException {
        Docent docent = loadDocent(id);
        if (docent == null) {
            throw new EntityNotFoundException();
        }
        docentDAO.delete(docent);
    }

    @Override
    public Docent findByForenameAndSurname(String forename, String surname) {
        return docentDAO.findByForenameAndSurname(forename, surname);
    }
}
