package de.nordakademie.iaa.service;

import de.nordakademie.iaa.model.Lecture;
import de.nordakademie.iaa.service.exception.EntityNotFoundException;

import java.util.List;


public interface LectureService {

    /**
     * Stores the given lecture into the database.
     *
     * @param lecture the lecture to be saved.
     */
    void saveLecture(Lecture lecture);

    /**
     * List all lectures currently stored in the database.
     *
     * @return a list of Lecture entities. If no lecture was found an empty list is
     * returned.
     */
    List<Lecture> listLectures();

    /**
     * Returns the lecture identified by the given id.
     *
     * @param id The identifier.
     * @return the found entity or {@code null} if no entity was found with given identifier.
     */
    Lecture loadLecture(Long id);

    /**
     * Deletes the lecture with the given id.
     *
     * @param id The identifier.
     * @throws EntityNotFoundException if no lecture could be found for the given id.
     */
    void deleteLecture(Long id) throws EntityNotFoundException;

}
