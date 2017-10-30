package de.nordakademie.iaa.service.impl;

import de.nordakademie.iaa.dao.LectureDAO;
import de.nordakademie.iaa.model.Lecture;
import de.nordakademie.iaa.service.LectureService;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LectureServiceImpl implements LectureService {

    private LectureDAO lectureDAO;

    @Autowired
    public LectureServiceImpl(LectureDAO lectureDAO) {
        this.lectureDAO = lectureDAO;
    }

    @Override
    public void saveLecture(Lecture lecture) {
        lectureDAO.save(lecture);
    }

    @Override
    public List<Lecture> listLectures() {
        return lectureDAO.findAll();
    }

    @Override
    public Lecture loadLecture(Long id) {
        return lectureDAO.findOne(id);
    }

    @Override
    public void deleteLecture(Long id) throws EntityNotFoundException {
        Lecture lecture = loadLecture(id);
        if (lecture == null) {
            throw new EntityNotFoundException();
        }
        lectureDAO.delete(lecture);
    }
}
