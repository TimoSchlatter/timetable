package de.nordakademie.iaa.service.impl;

import de.nordakademie.iaa.dao.CohortDAO;
import de.nordakademie.iaa.model.Cohort;
import de.nordakademie.iaa.service.CohortService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import de.nordakademie.iaa.service.exception.NotEnoughChangeoverTimeProvidedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CohortServiceImpl implements CohortService {

    private CohortDAO cohortDAO;

    @Autowired
    public CohortServiceImpl(CohortDAO cohortDAO) {
        this.cohortDAO = cohortDAO;
    }

    @Override
    public void saveCohort(Cohort cohort) throws NotEnoughChangeoverTimeProvidedException {
        if (cohort.getMinChangeoverTime() < 15) {
            throw new NotEnoughChangeoverTimeProvidedException();
        }
        cohortDAO.save(cohort);
    }

    @Override
    public List<Cohort> listCohorts() {
        return cohortDAO.findAll();
    }

    @Override
    public Cohort loadCohort(Long id) {
        return cohortDAO.findOne(id);
    }

    @Override
    public void deleteCohort(Long id) throws EntityNotFoundException {
        Cohort cohort = loadCohort(id);
        if (cohort == null) {
            throw new EntityNotFoundException();
        }
        cohortDAO.delete(cohort);
    }

    @Override
    public Cohort findByName(String name) {
        return cohortDAO.findByName(name);
    }
}
