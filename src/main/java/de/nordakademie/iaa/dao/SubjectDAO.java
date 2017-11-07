package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Course;
import de.nordakademie.iaa.model.Module;
import de.nordakademie.iaa.model.Subject;
import de.nordakademie.iaa.model.SubjectType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectDAO extends JpaRepository<Subject,Long>,BaseDAO<Subject, Long> {

    /**
     *
     * @param subjectType
     * @param module
     * @return
     */
    Subject findBySubjectTypeAndModule(SubjectType subjectType, Module module);

    /**
     *
     * @param subjectType
     * @return
     */
    List<Subject> findBySubjectType(SubjectType subjectType);

    /**
     *
     * @param course
     */
    void deleteByModule(Course course);
}
