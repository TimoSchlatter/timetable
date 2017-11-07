package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Module;
import de.nordakademie.iaa.model.Subject;
import de.nordakademie.iaa.model.SubjectType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectDAO extends JpaRepository<Subject,Long>,BaseDAO<Subject, Long> {

    /**
     * Find a subject by its type and module
     *
     * @param subjectType the type of the subject
     * @param module the module of the subject
     * @return the subject with the given type and module
     */
    Subject findBySubjectTypeAndModule(SubjectType subjectType, Module module);

    /**
     *Find subject of a specific type
     *
     * @param subjectType the type of the subject
     * @return the subjects that are the given type
     */
    List<Subject> findBySubjectType(SubjectType subjectType);

    /**
     * Delete subjects that contain the given module
     *
     * @param module the module of the subject
     */
    void deleteByModule(Module module);
}
