package de.nordakademie.iaa.service.impl;

import de.nordakademie.iaa.dao.SubjectDAO;
import de.nordakademie.iaa.model.Module;
import de.nordakademie.iaa.model.Subject;
import de.nordakademie.iaa.model.SubjectType;
import de.nordakademie.iaa.service.SubjectService;
import de.nordakademie.iaa.service.exception.NotEnoughChangeoverTimeProvidedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for subject entities.
 *
 * @author Timo Schlatter
 */
@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {

    private static final int EXAM_MIN_CHANGEOVER_TIME = 30;
    private final SubjectDAO subjectDAO;

    @Autowired
    public SubjectServiceImpl(SubjectDAO subjectDAO) {
        this.subjectDAO = subjectDAO;
    }

    @Override
    public void saveSubject(Subject subject) throws NotEnoughChangeoverTimeProvidedException {
        int actualMinChangeoverTime = subject.getMinChangeoverTime();
        if (subject.getSubjectType().equals(SubjectType.EXAM) &&
                actualMinChangeoverTime < EXAM_MIN_CHANGEOVER_TIME) {
            throw new NotEnoughChangeoverTimeProvidedException(subject, EXAM_MIN_CHANGEOVER_TIME,
                    actualMinChangeoverTime);
        }
        subjectDAO.save(subject);
    }

    @Override
    public List<Subject> listSubjects() {
        return subjectDAO.findAll();
    }

    @Override
    public Subject loadSubject(Long id) {
        return subjectDAO.findOne(id);
    }

    @Override
    public boolean deleteSubject(Long id) {
        Subject subject = loadSubject(id);
        if (subject == null) {
            return false;
        }
        subjectDAO.delete(subject);
        return true;
    }

    @Override
    public Subject findSubjectBySubjectTypeAndModule(SubjectType subjectType, Module module) {
        return subjectDAO.findBySubjectTypeAndModule(subjectType, module);
    }

    @Override
    public void deleteSubjectByModule(Module module) {
        subjectDAO.deleteByModule(module);
    }
}