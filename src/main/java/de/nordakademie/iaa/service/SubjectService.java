package de.nordakademie.iaa.service;

import de.nordakademie.iaa.model.Module;
import de.nordakademie.iaa.model.Subject;
import de.nordakademie.iaa.model.SubjectType;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;

import java.util.List;


public interface SubjectService {

    /**
     * Stores the given subject into the database.
     *
     * @param subject the subject to be saved.
     */
    void saveSubject(Subject subject);

    /**
     * List all subjects currently stored in the database.
     *
     * @return a list of Subject entities. If no subject was found an empty list is
     * returned.
     */
    List<Subject> listSubjects();

    /**
     * Returns the subject identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    Subject loadSubject(Long id);

    /**
     * Deletes the subject with the given id.
     *
     * @param id The identifier.
     * @throws EntityNotFoundException if no subject could be found for the given id.
     */
    void deleteSubject(Long id) throws EntityNotFoundException;

    /**
     * Retrieves a subject by its subjectType and module.
     * @param subjectType the identifier.
     * @param module the identifier.
     * @return the found subject or null if no subject was found with given identifiers.
     */
    Subject findSubjectBySubjectTypeAndModule(SubjectType subjectType, Module module);

    /**
     * Retrieves a subject by its subjectType.
     * @param subjectType the identifier.
     * @return the found subject or null if no subject was found with given identifier.
     */
    List<Subject> findSubjectBySubjectType(SubjectType subjectType);

    /**
     * Delete subjects that contain the given module
     *
     * @param module the module of the subject
     */
    void deleteSubjectByModule(Module module);
}
