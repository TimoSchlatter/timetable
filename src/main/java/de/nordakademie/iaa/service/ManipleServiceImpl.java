package de.nordakademie.iaa.service;

import de.nordakademie.iaa.dao.ManipleDAO;
import de.nordakademie.iaa.model.Maniple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ManipleServiceImpl implements ManipleService {

    private ManipleDAO manipleDAO;

    @Autowired
    public ManipleServiceImpl(ManipleDAO manipleDAO) {
        this.manipleDAO = manipleDAO;
    }

    @Override
    public void saveManiple(Maniple maniple) {
        manipleDAO.save(maniple);
    }

    public List<Maniple> listManiples() {
        return manipleDAO.findAll();
    }

    @Override
    public Maniple loadManiple(Long id) {
        return manipleDAO.findOne(id);
    }

    @Override
    public void deleteManiple(Long id) throws EntityNotFoundException {
        Maniple maniple = loadManiple(id);
        if (maniple == null) {
            throw new EntityNotFoundException();
        }
        manipleDAO.delete(maniple);
    }
}
