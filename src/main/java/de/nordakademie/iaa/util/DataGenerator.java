package de.nordakademie.iaa.util;

import de.nordakademie.iaa.model.*;
import de.nordakademie.iaa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Transactional
public class DataGenerator {

    private CenturyService centuryService;
    private CohortService cohortService;
    private CourseService courseService;
    private DocentService docentService;
    private EventService eventService;
    private ExamService examService;
    private LectureService lectureService;
    private ManipleService manipleService;
    private RoomService roomService;
    private SeminarService seminarService;

    @Autowired
    public DataGenerator(CenturyService centuryService, CohortService cohortService, CourseService courseService, DocentService docentService, EventService eventService, ExamService examService, LectureService lectureService, ManipleService manipleService, RoomService roomService, SeminarService seminarService) {
        this.centuryService = centuryService;
        this.cohortService = cohortService;
        this.courseService = courseService;
        this.docentService = docentService;
        this.eventService = eventService;
        this.examService = examService;
        this.lectureService = lectureService;
        this.manipleService = manipleService;
        this.roomService = roomService;
        this.seminarService = seminarService;
    }

    /**
     * Warning: Be careful when changing this order!
     */
    //    @PostConstruct
    public void createData() {
        createRooms();
        createDocents();
        createCourses();
        createSeminars();
        createLectures();
        createExams();
        createGroups();
        createEvents();
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
    }

    private void createDocents() {
        docentService.saveDocent(new Docent("", "Uwe", "Adamczak", "", "", false, 30));
        docentService.saveDocent(new Docent("", "Martin", "Müller", "", "Dr.", false, 30));
        docentService.saveDocent(new Docent("", "Soenke", "Stange", "", "", false, 30));
        docentService.saveDocent(new Docent("", "Carlo ", "Düllings", "", "", false, 30));
        docentService.saveDocent(new Docent("", "Clemens", "Sietas", "", "", false, 30));
        docentService.saveDocent(new Docent("", "Anna Katharina", "Bartel", "", "", false, 30));
        docentService.saveDocent(new Docent("", "Andrea", "Denke", "", "Dr.", false, 30));
        docentService.saveDocent(new Docent("", "Michael ", "Bregulla", "", "", false, 30));
        docentService.saveDocent(new Docent("", "Kersten", "Steinke", "", "", false, 30));

        docentService.saveDocent(new Docent("stefan.reichert@nordakademie.de", "Stefan", "Reichert", "", "", false, 30));
        docentService.saveDocent(new Docent("stephan.anft@nordakademie.de", "Stephan ", "Anft", "", "", false, 30));
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
        courseService.saveCourse(new Course("I", 103, "Technische Grundlagen der Informatik 1", "TGdI 1"));
        courseService.saveCourse(new Course("I", 104, "Technische Grundlagen der Informatik 2", "TGdI 2"));
        courseService.saveCourse(new Course("I", 107, "Algorithmen und Datenstrukturen", "AlgoDat"));
        courseService.saveCourse(new Course("I", 128, "Englisch 1"));
        courseService.saveCourse(new Course("I", 129, "Englisch 2"));
        courseService.saveCourse(new Course("I", 115, "Betriebswirtschaftliche Anwendungen", "Betr. Anw."));
        courseService.saveCourse(new Course("I", 143, "Praxis der Softwareentwicklung", "PdSe"));
        courseService.saveCourse(new Course("I", 148, "Internet Anwendungsarchitekturen", "IAA"));
        courseService.saveCourse(new Course("I", 149, "Geschäftsprozessmodellierung und Qualitätsmanagement", "GPM und QM"));
        courseService.saveCourse(new Course("I", 150, "Seminar: Ausgewählte Kapitel der Wirtschaftsinformatik", "AKdW"));
        courseService.saveCourse(new Course("I", 151, "Softwaretechnik"));
        courseService.saveCourse(new Course("I", 154, "Allgemeine Volkswirtschaftslehre", "AVWL"));
        courseService.saveCourse(new Course("I", 155, "Allgemeine Betriebswirtschaftslehre", "ABWL"));
        courseService.saveCourse(new Course("I", 157, "Logistik / Operations Management", "Log./ OM"));
        courseService.saveCourse(new Course("I", 158, "Controlling"));
    }

    private void createSeminars() {
        seminarService.saveSeminar(new Seminar("Der Business Plan - Mit System zum Erfolg", 25));
        seminarService.saveSeminar(new Seminar("Wirksame Tools für erfolgreiches Projektmanagement", 20));
        seminarService.saveSeminar(new Seminar("Excel VBA für Einsteiger", 25));
        seminarService.saveSeminar(new Seminar("Emotionale Intelligenz", 25));
        seminarService.saveSeminar(new Seminar("Sourcecodeverwaltung mit Git und GitHub", 25));
        seminarService.saveSeminar(new Seminar("Zeit- und Selbstmanagement", 20));
        seminarService.saveSeminar(new Seminar("Große Dokumente in Word", 25));
        seminarService.saveSeminar(new Seminar("Networking Excellence", 20));
        seminarService.saveSeminar(new Seminar("Business-Knigge", 20));
    }

    private void createLectures() {
        courseService.listCourses().forEach(course -> lectureService.saveLecture(new Lecture(15, course)));
    }

    private void createExams() {
        courseService.listCourses().forEach(course -> examService.saveExam(new Exam(30, course)));
    }

    private void createGroups() {
//        Century centuryA14a = new Century("A14a", 26);
//        Century centuryA14b = new Century("A14b", 27);
//        Century centuryI14a = new Century("I14a", 35);
//        Century centuryI14b = new Century("I14b", 30);
//        Century centuryI14c = new Century("I14c", 30);
//        Century centuryW14a = new Century("W14a", 40);
//        Century centuryW14b = new Century("W14b", 35);
//        Century centuryW14c = new Century("W14c", 35);
//        Arrays.asList(centuryA14a, centuryA14b, centuryI14a, centuryI14b, centuryI14c, centuryW14a, centuryW14b,
//                centuryW14c).forEach(centuryService::saveCentury);
//
//        Maniple manipleA14 = new Maniple("A14");
//        Maniple manipleI14 = new Maniple("I14");
//        Maniple manipleW14 = new Maniple("W14");
//        Arrays.asList(manipleA14, manipleI14, manipleW14).forEach(manipleService::saveManiple);
//        manipleA14.setCenturies(Arrays.asList(centuryA14a, centuryA14b));
//        manipleI14.setCenturies(Arrays.asList(centuryI14a, centuryI14b, centuryI14c));
//        manipleW14.setCenturies(Arrays.asList(centuryW14a, centuryW14b, centuryW14c));
//
//        Cohort cohort14 = new Cohort("14");
//        cohortService.saveCohort(cohort14);
//        cohort14.setManiples(Arrays.asList(manipleA14, manipleI14, manipleW14));

        for (int i = 14; i < 18; i++) {
            String[] centuryNames = new String[]{"a", "b", "c", "d"};
            Cohort cohort = new Cohort(Integer.toString(i), 30);
            cohortService.saveCohort(cohort);
            Arrays.asList("A" + i, "B" + i, "I" + i, "W" + i).forEach(manipleName -> {
                Maniple maniple = new Maniple(manipleName, 30);
                manipleService.saveManiple(maniple);
                ThreadLocalRandom random = ThreadLocalRandom.current();
                for (int centuryNumber = 0; centuryNumber < random.nextInt(2, centuryNames.length+1); centuryNumber++){
                    Century century = new Century(manipleName + centuryNames[centuryNumber], 15, random.nextInt(25, 36));
                    centuryService.saveCentury(century);
                    maniple.addCentury(century);
                }
                cohort.addManiple(maniple);
            });
        }
    }

    private void createEvents() {
        Set<Room> eventRooms = new HashSet<>(Arrays.asList(roomService.findByBuildingAndNumber("A", "001")));
        List<Docent> docents = docentService.listDocents();
        Set<Docent> eventDocents = new HashSet<>();
        docents.stream().filter(docent -> docent.getForename().contains("Uwe")).findFirst().ifPresent(eventDocents::add);
        docents.stream().filter(docent -> docent.getForename().contains("Joachim")).findFirst().ifPresent(eventDocents::add);
        Group eventGroup = centuryService.listCenturies().stream().filter(century -> century.getName().equals("I14a")).findFirst().get();
        LocalDate date = LocalDate.of(2017, Month.DECEMBER, 12);
        LocalTime startTime = LocalTime.of(9, 15);
        LocalTime endTime = LocalTime.of(11, 30);
        Course course = courseService.listCourses().stream().filter(c -> c.getField().equals("I") && c.getNumber() == 104).findFirst().get();
        Subject eventSubject = lectureService.listLectures().stream().filter(lecture -> lecture.getCourse().equals(course)).findFirst().get();
        Event event = new Event(eventRooms, eventDocents, eventGroup, date, startTime, endTime, eventSubject);
        eventService.saveEvent(event);
    }
}