package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by arvid on 25.10.17.
 */
public interface ExamDAO extends JpaRepository<Exam,Long>, BaseDAO<Exam, Long> {
}
