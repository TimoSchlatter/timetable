package de.nordakademie.iaa.service;

import de.nordakademie.iaa.model.Exam;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;

import java.util.List;


public interface ExamService {

    /**
     * Stores the given exam into the database.
     *
     * @param exam the exam to be saved.
     */
    void saveExam(Exam exam);

    /**
     * List all exams currently stored in the database.
     *
     * @return a list of Exam entities. If no exam was found an empty list is
     * returned.
     */
    List<Exam> listExams();

    /**
     * Returns the exam identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    Exam loadExam(Long id);

    /**
     * Deletes the exam with the given id.
     *
     * @param id The identifier.
     * @throws EntityNotFoundException if no exam could be found for the given id.
     */
    void deleteExam(Long id) throws EntityNotFoundException;

}
