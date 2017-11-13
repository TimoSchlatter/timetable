package de.nordakademie.iaa.service.impl;

import de.nordakademie.iaa.dao.DocentDAO;
import de.nordakademie.iaa.model.Docent;
import de.nordakademie.iaa.service.DocentService;
import de.nordakademie.iaa.service.exception.NotEnoughChangeoverTimeProvidedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for docent entities.
 *
 * @author Timo Schlatter
 */
@Service
@Transactional
public class DocentServiceImpl implements DocentService {

    private static final int MIN_CHANGEOVER_TIME = 15;
    private final DocentDAO docentDAO;

    @Autowired
    public DocentServiceImpl(DocentDAO docentDAO) {
        this.docentDAO = docentDAO;
    }

    @Override
    public void saveDocent(Docent docent) throws NotEnoughChangeoverTimeProvidedException {
        final int actualMinChangeoverTime = docent.getMinChangeoverTime();
        if (actualMinChangeoverTime < MIN_CHANGEOVER_TIME) {
            throw new NotEnoughChangeoverTimeProvidedException(docent, MIN_CHANGEOVER_TIME, actualMinChangeoverTime);
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
    public boolean deleteDocent(Long id) {
        Docent docent = loadDocent(id);
        if (docent == null) {
            return false;
        }
        docentDAO.delete(docent);
        return true;
    }

    @Override
    public Docent findDocentByForenameAndSurname(String forename, String surname) {
        return docentDAO.findByForenameAndSurname(forename, surname);
    }
}