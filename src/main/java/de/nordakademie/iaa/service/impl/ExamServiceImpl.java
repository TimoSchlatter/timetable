package de.nordakademie.iaa.service.impl;

import de.nordakademie.iaa.dao.ExamDAO;
import de.nordakademie.iaa.model.Exam;
import de.nordakademie.iaa.service.ExamService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExamServiceImpl implements ExamService {

    private ExamDAO examDAO;

    @Autowired
    public ExamServiceImpl(ExamDAO examDAO) {
        this.examDAO = examDAO;
    }

    @Override
    public void saveExam(Exam exam) {
        examDAO.save(exam);
    }

    public List<Exam> listExams() {
        return examDAO.findAll();
    }

    @Override
    public Exam loadExam(Long id) {
        return examDAO.findOne(id);
    }

    @Override
    public void deleteExam(Long id) throws EntityNotFoundException {
        Exam exam = loadExam(id);
        if (exam == null) {
            throw new EntityNotFoundException();
        }
        examDAO.delete(exam);
    }
}
