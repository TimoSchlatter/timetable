package de.nordakademie.iaa.service;

import de.nordakademie.iaa.dao.CohortDAO;
import de.nordakademie.iaa.model.Cohort;
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
    public void saveCohort(Cohort cohort) {
        cohortDAO.save(cohort);
    }

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
}
