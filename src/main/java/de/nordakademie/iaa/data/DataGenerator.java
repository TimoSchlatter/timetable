package de.nordakademie.iaa.data;

import de.nordakademie.iaa.model.*;
import de.nordakademie.iaa.service.*;
import de.nordakademie.iaa.service.exception.NotEnoughChangeoverTimeProvidedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static de.nordakademie.iaa.model.SeminarType.*;

/**
 * Utility class for generating dummy data.
 *
 * @author Timo Schlatter
 */
@Component
@Transactional
public class DataGenerator {

    private final CenturyService centuryService;
    private final CohortService cohortService;
    private final CourseService courseService;
    private final DocentService docentService;
    private final EventService eventService;
    private final ManipleService manipleService;
    private final RoomService roomService;
    private final SeminarService seminarService;
    private final SubjectService subjectService;
    private final SeminarGroupService seminarGroupService;

    @Autowired
    public DataGenerator(CenturyService centuryService, CohortService cohortService, CourseService courseService, DocentService docentService, EventService eventService, ManipleService manipleService, RoomService roomService, SeminarService seminarService, SubjectService subjectService, SeminarGroupService seminarGroupService) {
        this.centuryService = centuryService;
        this.cohortService = cohortService;
        this.courseService = courseService;
        this.docentService = docentService;
        this.eventService = eventService;
        this.manipleService = manipleService;
        this.roomService = roomService;
        this.seminarService = seminarService;
        this.subjectService = subjectService;
        this.seminarGroupService = seminarGroupService;
    }

    /**
     * Generates dummy date.
     */
    public void createData() throws Exception {
        // Warning: Be careful when changing this order!
        createRooms();
        createDocents();
        createCourses();
        createSeminars();
        createGroups();
        createSeminarGroups();
        createSubjects();
        createEvents();
    }

    private void createSeminarGroups() {
        seminarGroupService.saveSeminarGroup(new SeminarGroup("Max. 15 Studenten", 30, 15));
        seminarGroupService.saveSeminarGroup(new SeminarGroup("Max. 20 Studenten", 30, 20));
        seminarGroupService.saveSeminarGroup(new SeminarGroup("Max. 25 Studenten", 30, 25));
        seminarGroupService.saveSeminarGroup(new SeminarGroup("Max. 30 Studenten", 30, 30));
    }

    private void createRooms() {
        roomService.saveRoom(new Room(20, "A", 40, "001", RoomType.COMPUTERROOM));
        roomService.saveRoom(new Room(15, "A", 40, "002", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(15, "A", 40, "003", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(15, "A", 40, "004", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(20, "A", 40, "101", RoomType.COMPUTERROOM));
        roomService.saveRoom(new Room(20, "A", 25, "102", RoomType.COMPUTERROOM));

        roomService.saveRoom(new Room(20, "C", 30, "001", RoomType.LABORATORY));
        roomService.saveRoom(new Room(20, "C", 35, "002", RoomType.LABORATORY));
        roomService.saveRoom(new Room(15, "C", 40, "003", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(15, "C", 40, "004", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(20, "C", 35, "101", RoomType.COMPUTERROOM));
        roomService.saveRoom(new Room(20, "C", 25, "102", RoomType.COMPUTERROOM));
        roomService.saveRoom(new Room(15, "C", 40, "103", RoomType.LECTUREROOM));

        roomService.saveRoom(new Room(15, "D", 20, "001", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(15, "D", 20, "002", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(15, "D", 20, "003", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(15, "D", 20, "004", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(30, "D", 20, "005", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(15, "D", 20, "101", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(15, "D", 20, "102", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(15, "D", 20, "103", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(15, "D", 20, "104", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(30, "D", 20, "105", RoomType.LECTUREROOM));

        roomService.saveRoom(new Room(30, "H", 1000, "001", RoomType.LECTUREROOM));
    }

    private void createDocents() throws NotEnoughChangeoverTimeProvidedException {
        docentService.saveDocent(new Docent("", "Uwe", "Adamczak", "", "", false, 30));
        docentService.saveDocent(new Docent("", "Martin", "Müller", "", "Dr.", false, 30));
        docentService.saveDocent(new Docent("", "Soenke", "Stange", "", "", false, 30));
        docentService.saveDocent(new Docent("", "Carlo", "Düllings", "", "", false, 30));
        docentService.saveDocent(new Docent("", "Clemens", "Sietas", "", "", false, 30));
        docentService.saveDocent(new Docent("", "Anna Katharina", "Bartel", "", "", false, 30));
        docentService.saveDocent(new Docent("", "Andrea", "Denke", "", "Dr.", false, 30));
        docentService.saveDocent(new Docent("", "Michael", "Bregulla", "", "", false, 30));
        docentService.saveDocent(new Docent("", "Kersten", "Steinke", "", "", false, 30));

        docentService.saveDocent(new Docent("stefan.reichert@nordakademie.de", "Stefan", "Reichert", "", "", false, 30));
        docentService.saveDocent(new Docent("stephan.anft@nordakademie.de", "Stephan", "Anft", "", "", false, 30));
        docentService.saveDocent(new Docent("bjoern-helge.busch@nordakademie.de", "Björn-Helge", "Busch", "+49 (0) 4121 4090-40", "", false, 30));
        docentService.saveDocent(new Docent("ludolph@goldex.de", "Fred", "Ludolph", "+49 (0) 4121 4090-40", "", false, 30));
        docentService.saveDocent(new Docent("andrew.small@nordakademie.de", "Andrew", "Small", "+49 (0) 4121 4090-40", "M.A. (TESOL)", false, 30));
        docentService.saveDocent(new Docent("stover@nordakademie.de", "Jason", "Stover", "+49 (0) 4121 4090-40", "Diplom-Theologe", false, 30));

        docentService.saveDocent(new Docent("uwe.neuhaus@nordakademie.de", "Uwe", "Neuhaus", "04121 4090-543", "", true, 20));
        docentService.saveDocent(new Docent("joachim.sauer@nordakademie.de", "Joachim", "Sauer", "04121 4090-441", "Prof. Dr.", true, 30));
        docentService.saveDocent(new Docent("hinrich.schroeder@nordakademie.de", "Hinrich", "Schröder", "04018520656", "Prof. Dr.", true, 30));
        docentService.saveDocent(new Docent("michael.skall@nordakademie.de", "Michael", "Skall", "04121 4090-449", "Prof. Dr.", true, 30));
        docentService.saveDocent(new Docent("f.zimmermann@nordakademie.de", "Frank", "Zimmermann", "04121 4090-447", "Prof. Dr.", true, 30));
        docentService.saveDocent(new Docent("matthias.finck@nordakademie.de", "Matthias", "Finck", "04121 4090-445", "Prof. Dr.", true, 30));
        docentService.saveDocent(new Docent("frank.fuerstenberg@nordakademie.de", "Frank", "Fürstenberg", "+49 4121 4090-422", "Prof. Dr.-Ing.", true, 30));
    }

    private void createCourses() {
        courseService.saveCourse(new Course("Technische Grundlagen der Informatik 1", "TGdI 1", "I", 103));
        courseService.saveCourse(new Course("Technische Grundlagen der Informatik 2", "TGdI 2", "I", 104));
        courseService.saveCourse(new Course("Algorithmen und Datenstrukturen", "AlgoDat", "I", 107));
        courseService.saveCourse(new Course("Englisch 1", "I", 128));
        courseService.saveCourse(new Course("Englisch 2", "I", 129));
        courseService.saveCourse(new Course("Betriebswirtschaftliche Anwendungen", "Betr. Anw.", "I", 115));
        courseService.saveCourse(new Course("Praxis der Softwareentwicklung", "PdSe", "I", 143));
        courseService.saveCourse(new Course("Internet Anwendungsarchitekturen", "IAA", "I", 148));
        courseService.saveCourse(new Course("Geschäftsprozessmodellierung & Qualitätsmanagement", "GPM & QM", "I", 149));
        courseService.saveCourse(new Course("Seminar: Ausgewählte Kapitel der Wirtschaftsinformatik", "AKdW", "I", 150));
        courseService.saveCourse(new Course("Softwaretechnik", "I", 151));
        courseService.saveCourse(new Course("Allgemeine Volkswirtschaftslehre", "AVWL", "I", 154));
        courseService.saveCourse(new Course("Allgemeine Betriebswirtschaftslehre", "ABWL", "I", 155));
        courseService.saveCourse(new Course("Logistik & Operations Management", "Log. & OM", "I", 157));
        courseService.saveCourse(new Course("Controlling", "I", 158));
    }

    private void createSeminars() {
        seminarService.saveSeminar(new Seminar("Business-Knigge", KEY_QUALIFICATION));
        seminarService.saveSeminar(new Seminar("Case studies of Export business and Import business in Practice of industrial goods in/from non-EU", INTERNATIONAL));
        seminarService.saveSeminar(new Seminar("Der Business Plan - Mit System zum Erfolg", KEY_QUALIFICATION));
        seminarService.saveSeminar(new Seminar("Einführung Wirtschaftsmediation", ETHICS_SOCIAL));
        seminarService.saveSeminar(new Seminar("Emotionale Intelligenz", ETHICS_SOCIAL));
        seminarService.saveSeminar(new Seminar("Excel VBA für Einsteiger", KEY_QUALIFICATION));
        seminarService.saveSeminar(new Seminar("Große Dokumente in Word", KEY_QUALIFICATION));
        seminarService.saveSeminar(new Seminar("Networking Excellence", KEY_QUALIFICATION));
        seminarService.saveSeminar(new Seminar("Sourcecodeverwaltung mit Git und GitHub", KEY_QUALIFICATION));
        seminarService.saveSeminar(new Seminar("Virtual Power Teams", INTERNATIONAL));
        seminarService.saveSeminar(new Seminar("Wirksame Tools für erfolgreiches Projektmanagement", KEY_QUALIFICATION));
        seminarService.saveSeminar(new Seminar("Young Sales Professional Training", OTHER));
        seminarService.saveSeminar(new Seminar("Zeit- und Selbstmanagement", OTHER));
    }

    private void createSubjects() {
        courseService.listCourses().forEach(course -> subjectService.saveSubject(new Subject(15, SubjectType.LECTURE, course)));
        courseService.listCourses().forEach(course -> subjectService.saveSubject(new Subject(30, SubjectType.EXAM, course)));
        seminarService.listSeminars().forEach(seminar -> subjectService.saveSubject(new Subject(20, SubjectType.SEMINAR, seminar)));
    }

    private void createGroups() throws NotEnoughChangeoverTimeProvidedException {
        for (int i = 14; i < 18; i++) {
            String[] centuryNames = new String[]{"a", "b", "c", "d"};
            Cohort cohort = new Cohort(Integer.toString(i), 30);
            cohortService.saveCohort(cohort);
            Arrays.asList("A" + i, "B" + i, "I" + i, "W" + i).forEach(manipleName -> {
                try {
                    Maniple maniple = new Maniple(manipleName, 30);
                    manipleService.saveManiple(maniple);
                    ThreadLocalRandom random = ThreadLocalRandom.current();
                    for (int centuryNumber = 0; centuryNumber < random.nextInt(2, centuryNames.length + 1); centuryNumber++) {
                        try {
                            Century century = new Century(manipleName + centuryNames[centuryNumber], 15, random.nextInt(25, 36));
                            centuryService.saveCentury(century);
                            maniple.addCentury(century);
                        } catch (NotEnoughChangeoverTimeProvidedException ignored) {
                        }
                    }
                    cohort.addManiple(maniple);
                } catch (NotEnoughChangeoverTimeProvidedException ignored) {
                }
            });
        }
    }

    private void createEvents() throws Exception {
        LocalDate date = LocalDate.of(2017, Month.OCTOBER, 30);
        LocalTime startTime = LocalTime.of(9, 15);
        LocalTime endTime = LocalTime.of(11, 30);
        Set<Room> rooms = new HashSet<>(Arrays.asList(
                roomService.findRoomByBuildingAndNumber("A", "001"),
                roomService.findRoomByBuildingAndNumber("A", "002")));
        Set<Docent> docents = new HashSet<>(Arrays.asList(
                docentService.findDocentByForenameAndSurname("Uwe", "Neuhaus"),
                docentService.findDocentByForenameAndSurname("Joachim", "Sauer")));
        Group group = centuryService.findCenturyByName("I14a");
        Subject subject = subjectService.findSubjectBySubjectTypeAndModule(SubjectType.LECTURE,
                courseService.findCourseByTitle("Softwaretechnik"));
        saveEvent(date, startTime, endTime, rooms, docents, group, subject, 10);

        startTime = LocalTime.of(12, 15);
        endTime = LocalTime.of(16, 30);
        docents = new HashSet<>(Arrays.asList(
                docentService.findDocentByForenameAndSurname("Uwe", "Adamczak"),
                docentService.findDocentByForenameAndSurname("Joachim", "Sauer")));
        subject = subjectService.findSubjectBySubjectTypeAndModule(SubjectType.LECTURE,
                courseService.findCourseByTitle("Allgemeine Volkswirtschaftslehre"));
        saveEvent(date, startTime, endTime, rooms, docents, group, subject, 10);

        date = LocalDate.of(2017, Month.OCTOBER, 29);
        startTime = LocalTime.of(9, 15);
        endTime = LocalTime.of(11, 30);
        docents = new HashSet<>(Arrays.asList(docentService.findDocentByForenameAndSurname("Soenke", "Stange")));
        subject = subjectService.findSubjectBySubjectTypeAndModule(SubjectType.LECTURE,
                courseService.findCourseByTitle("Controlling"));
        saveEvent(date, startTime, endTime, rooms, docents, group, subject, 10);

        startTime = LocalTime.of(13, 15);
        endTime = LocalTime.of(18, 30);
        subject = subjectService.findSubjectBySubjectTypeAndModule(SubjectType.LECTURE,
                courseService.findCourseByTitle("Allgemeine Betriebswirtschaftslehre"));
        saveEvent(date, startTime, endTime, rooms, docents, group, subject, 10);

        date = LocalDate.of(2017, Month.OCTOBER, 31);
        startTime = LocalTime.of(12, 15);
        endTime = LocalTime.of(14, 30);
        subject = subjectService.findSubjectBySubjectTypeAndModule(SubjectType.EXAM,
                courseService.findCourseByTitle("Englisch 2"));
        assert subject != null;
        saveEvent(date, startTime, endTime, rooms, docents, group, subject, 10);

        date = LocalDate.of(2017, Month.NOVEMBER, 2);
        group = seminarGroupService.findSeminarGroupByName("Max. 25 Studenten");
        startTime = LocalTime.of(17, 15);
        endTime = LocalTime.of(20, 30);
        subject = subjectService.findSubjectBySubjectTypeAndModule(SubjectType.SEMINAR,
                seminarService.findSeminarByTitle("Große Dokumente in Word"));
        saveEvent(date, startTime, endTime, rooms, docents, group, subject, 10);

        startTime = LocalTime.of(20, 15);
        endTime = LocalTime.of(22, 30);
        subject = subjectService.findSubjectBySubjectTypeAndModule(SubjectType.SEMINAR,
                seminarService.findSeminarByTitle("Business-Knigge"));
        saveEvent(date, startTime, endTime, rooms, docents, group, subject, 10);

        date = LocalDate.of(2017, Month.NOVEMBER, 1);
        rooms = new HashSet<>(Arrays.asList(roomService.findRoomByBuildingAndNumber("H", "001")));
        docents = new HashSet<>(Arrays.asList(docentService.findDocentByForenameAndSurname("Michael", "Bregulla")));
        group = seminarGroupService.findSeminarGroupByName("Max. 20 Studenten");
        startTime = LocalTime.of(20, 15);
        endTime = LocalTime.of(22, 30);
        subject = subjectService.findSubjectBySubjectTypeAndModule(SubjectType.SEMINAR,
                seminarService.findSeminarByTitle("Business-Knigge"));
        saveEvent(date, startTime, endTime, rooms, docents, group, subject, 10);

        startTime = LocalTime.of(13, 15);
        endTime = LocalTime.of(16, 30);
        subject = subjectService.findSubjectBySubjectTypeAndModule(SubjectType.SEMINAR,
                seminarService.findSeminarByTitle("Große Dokumente in Word"));
        saveEvent(date, startTime, endTime, rooms, docents, group, subject, 10);
    }

    private void saveEvent(LocalDate date, LocalTime startTime, LocalTime endTime, Set<Room> rooms, Set<Docent> docents,
                           Group group, Subject subject, int repeatWeeks) throws Exception {
        rooms.forEach(room -> {
            if (room == null) {
                throw new IllegalArgumentException("Room must not be null!");
            }
        });
        docents.forEach(docent -> {
            if (docent == null) {
                throw new IllegalArgumentException("Docent must not be null!");
            }
        });
        assert group != null;
        assert subject != null;
        for (int i = 0; i < repeatWeeks; i++) {
            eventService.saveEvent(new Event(rooms, docents, group, date.plusDays(i * 7), startTime, endTime, subject));
        }
    }
}