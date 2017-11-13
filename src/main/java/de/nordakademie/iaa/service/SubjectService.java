package de.nordakademie.iaa.service;

import de.nordakademie.iaa.model.Module;
import de.nordakademie.iaa.model.Subject;
import de.nordakademie.iaa.model.SubjectType;
import de.nordakademie.iaa.service.exception.NotEnoughChangeoverTimeProvidedException;

import java.util.List;

/**
 * Service interface for subject entities.
 *
 * @author Timo Schlatter
 */
public interface SubjectService {

    /**
     * Stores the given subject into the database.
     *
     * @param subject the subject to be saved.
     */
    void saveSubject(Subject subject) throws NotEnoughChangeoverTimeProvidedException;

    /**
     * Lists all subjects currently stored in the database.
     *
     * @return a list of subject entities. If no subject was found an empty list is returned.
     */
    List<Subject> listSubjects();

    /**
     * Retrieves a subject by its id.
     *
     * @param id the identifier.
     * @return the found subject or {@code null} if no subject was found with the given id.
     */
    Subject loadSubject(Long id);

    /**
     * Deletes the subject with the given id.
     *
     * @param id the identifier.
     * @return <tt>true</tt> if the subject was deleted.
     */
    boolean deleteSubject(Long id);

    /**
     * Retrieves a subject by its type and module.
     *
     * @param subjectType the identifier.
     * @param module      the identifier.
     * @return the found subject or {@code null} if no subject was found with the given identifier.
     */
    Subject findSubjectBySubjectTypeAndModule(SubjectType subjectType, Module module);

    /**
     * Deletes subjects which contain the given module.
     *
     * @param module the identifier.
     */
    void deleteSubjectByModule(Module module);
}