package de.nordakademie.iaa.dao;

import de.nordakademie.iaa.model.Module;
import de.nordakademie.iaa.model.Subject;
import de.nordakademie.iaa.model.SubjectType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DAO for subject entities.
 *
 * @author Arvid Ottenberg
 */
public interface SubjectDAO extends JpaRepository<Subject, Long> {

    /**
     * Retrieves a subject by its type and module.
     *
     * @param subjectType the identifier.
     * @param module      the identifier.
     * @return the found subject or {@code null} if no subject was found with the given identifier.
     */
    Subject findBySubjectTypeAndModule(SubjectType subjectType, Module module);

    /**
     * Deletes subjects which contain the given module.
     *
     * @param module the identifier.
     */
    void deleteByModule(Module module);
}