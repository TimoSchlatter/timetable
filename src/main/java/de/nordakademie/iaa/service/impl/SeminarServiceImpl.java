package de.nordakademie.iaa.service.impl;

import de.nordakademie.iaa.dao.SeminarDAO;
import de.nordakademie.iaa.model.Seminar;
import de.nordakademie.iaa.service.SeminarService;
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

    @Override
    public List<Seminar> listSeminars() {
        return seminarDAO.findAll();
    }

    @Override
    public Seminar loadSeminar(Long id) {
        return seminarDAO.findOne(id);
    }

    @Override
    public boolean deleteSeminar(Long id) {
        Seminar seminar = loadSeminar(id);
        if (seminar == null) {
            return false;
        }
        seminarDAO.delete(seminar);
        return true;
    }

    @Override
    public Seminar findByTitle(String title) {
        return seminarDAO.findByTitle(title);
    }
}
