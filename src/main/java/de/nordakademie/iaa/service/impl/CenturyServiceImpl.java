package de.nordakademie.iaa.service.impl;

import de.nordakademie.iaa.dao.CenturyDAO;
import de.nordakademie.iaa.model.Century;
import de.nordakademie.iaa.service.CenturyService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import de.nordakademie.iaa.service.exception.NotEnoughChangeoverTimeProvidedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CenturyServiceImpl implements CenturyService {

    private CenturyDAO centuryDAO;

    @Autowired
    public CenturyServiceImpl(CenturyDAO centuryDAO) {
        this.centuryDAO = centuryDAO;
    }

    @Override
    public void saveCentury(Century century) throws NotEnoughChangeoverTimeProvidedException {
        if (century.getMinChangeoverTime() < 15) {
            throw new NotEnoughChangeoverTimeProvidedException();
        }
        centuryDAO.save(century);
    }

    @Override
    public List<Century> listCenturies() {
        return centuryDAO.findAll();
    }

    @Override
    public Century loadCentury(Long id) {
        return centuryDAO.findOne(id);
    }

    @Override
    public void deleteCentury(Long id) throws EntityNotFoundException {
        Century century = loadCentury(id);
        if (century == null) {
            throw new EntityNotFoundException();
        }
        centuryDAO.delete(century);
    }

    @Override
    public Century findByName(String name) {
        return centuryDAO.findByName(name);
    }
}
