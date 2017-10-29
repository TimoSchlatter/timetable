package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by arvid on 25.10.17.
 */
public interface LectureDAO extends JpaRepository<Lecture,Long>, BaseDAO<Lecture, Long> {
}
