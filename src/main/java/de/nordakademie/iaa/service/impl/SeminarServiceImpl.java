package de.nordakademie.iaa.service.impl;

import de.nordakademie.iaa.dao.SeminarDAO;
import de.nordakademie.iaa.model.Seminar;
import de.nordakademie.iaa.service.SeminarService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SeminarServiceImpl implements SeminarService {

    private SeminarDAO seminarDAO;

    @Autowired
    public SeminarServiceImpl(SeminarDAO seminarDAO) {
        this.seminarDAO = seminarDAO;
    }

    @Override
    public void saveSeminar(Seminar seminar) {
        seminarDAO.save(seminar);
    }

    public List<Seminar> listSeminars() {
        return seminarDAO.findAll();
    }

    @Override
    public Seminar loadSeminar(Long id) {
        return seminarDAO.findOne(id);
    }

    @Override
    public void deleteSeminar(Long id) throws EntityNotFoundException {
        Seminar seminar = loadSeminar(id);
        if (seminar == null) {
            throw new EntityNotFoundException();
        }
        seminarDAO.delete(seminar);
    }
}
