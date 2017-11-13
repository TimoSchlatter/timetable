package de.nordakademie.iaa.service.impl;

import de.nordakademie.iaa.dao.ManipleDAO;
import de.nordakademie.iaa.model.Maniple;
import de.nordakademie.iaa.service.ManipleService;
import de.nordakademie.iaa.service.exception.NotEnoughChangeoverTimeProvidedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for maniple entities.
 *
 * @author Timo Schlatter
 */
@Service
@Transactional
public class ManipleServiceImpl implements ManipleService {

    private static final int MIN_CHANGEOVER_TIME = 15;
    private final ManipleDAO manipleDAO;

    @Autowired
    public ManipleServiceImpl(ManipleDAO manipleDAO) {
        this.manipleDAO = manipleDAO;
    }

    @Override
    public void saveManiple(Maniple maniple) throws NotEnoughChangeoverTimeProvidedException {
        final int actualMinChangeoverTime = maniple.getMinChangeoverTime();
        if (actualMinChangeoverTime < MIN_CHANGEOVER_TIME) {
            throw new NotEnoughChangeoverTimeProvidedException(maniple, MIN_CHANGEOVER_TIME, actualMinChangeoverTime);
        }
        manipleDAO.save(maniple);
    }

    @Override
    public List<Maniple> listManiples() {
        return manipleDAO.findAll();
    }

    @Override
    public Maniple loadManiple(Long id) {
        return manipleDAO.findOne(id);
    }

    @Override
    public boolean deleteManiple(Long id) {
        Maniple maniple = loadManiple(id);
        if (maniple == null) {
            return false;
        }
        manipleDAO.delete(maniple);
        return true;
    }

    @Override
    public Maniple findManipleByName(String name) {
        return manipleDAO.findByName(name);
    }
}