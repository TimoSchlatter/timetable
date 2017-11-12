package de.nordakademie.iaa.data;

import de.nordakademie.iaa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Utility class for clearing the database.
 *
 * @author Timo Schlatter
 */
@Component
@Transactional
public class DatabaseResetter {

    private final CenturyService centuryService;
    private final CohortService cohortService;
    private final CourseService courseService;
    private final DocentService docentService;
    private final EventService eventService;
    private final ManipleService manipleService;
    private final RoomService roomService;
    private final SeminarGroupService seminarGroupService;
    private final SeminarService seminarService;
    private final SubjectService subjectService;

    @Autowired
    public DatabaseResetter(CenturyService centuryService, CohortService cohortService, CourseService courseService,
                            DocentService docentService, EventService eventService, ManipleService manipleService,
                            RoomService roomService, SeminarGroupService seminarGroupService,
                            SeminarService seminarService, SubjectService subjectService) {
        this.centuryService = centuryService;
        this.cohortService = cohortService;
        this.courseService = courseService;
        this.docentService = docentService;
        this.eventService = eventService;
        this.manipleService = manipleService;
        this.roomService = roomService;
        this.seminarGroupService = seminarGroupService;
        this.seminarService = seminarService;
        this.subjectService = subjectService;
    }

    /**
     * Removes all persisted entities from the database.
     */
    public void clearDatabase() {
        eventService.listEvents().forEach(event -> eventService.deleteEvent(event.getId()));
        subjectService.listSubjects().forEach(subject -> subjectService.deleteSubject(subject.getId()));
        seminarGroupService.listSeminarGroups()
                .forEach(seminarGroup -> seminarGroupService.deleteSeminarGroup(seminarGroup.getId()));
        cohortService.listCohorts().forEach(cohort -> cohortService.deleteCohort(cohort.getId()));
        seminarService.listSeminars().forEach(seminar -> seminarService.deleteSeminar(seminar.getId()));
        courseService.listCourses().forEach(course -> courseService.deleteCourse(course.getId()));
        docentService.listDocents().forEach(docent -> docentService.deleteDocent(docent.getId()));
        roomService.listRooms().forEach(room -> roomService.deleteRoom(room.getId()));
    }

    /**
     * Determines if there is at least one entity persisted in the database.
     *
     * @return <tt>true</tt> if not a single entity is persisted in the database.
     */
    public boolean isDatabaseEmpty() {
        return centuryService.listCenturies().isEmpty() && cohortService.listCohorts().isEmpty() &&
                courseService.listCourses().isEmpty() && docentService.listDocents().isEmpty() &&
                eventService.listEvents().isEmpty() && manipleService.listManiples().isEmpty() &&
                roomService.listRooms().isEmpty() && seminarGroupService.listSeminarGroups().isEmpty() &&
                seminarService.listSeminars().isEmpty() && subjectService.listSubjects().isEmpty();

    }
}