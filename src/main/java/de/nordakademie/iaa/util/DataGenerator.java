package de.nordakademie.iaa.util;

import de.nordakademie.iaa.model.*;
import de.nordakademie.iaa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static de.nordakademie.iaa.model.SeminarType.*;

@Component
@Transactional
public class DataGenerator {

    private CenturyService centuryService;
    private CohortService cohortService;
    private CourseService courseService;
    private DocentService docentService;
    private EventService eventService;
    private ManipleService manipleService;
    private RoomService roomService;
    private SeminarService seminarService;

    @Autowired
    public DataGenerator(CenturyService centuryService, CohortService cohortService, CourseService courseService, DocentService docentService, EventService eventService, ManipleService manipleService, RoomService roomService, SeminarService seminarService) {
        this.centuryService = centuryService;
        this.cohortService = cohortService;
        this.courseService = courseService;
        this.docentService = docentService;
        this.eventService = eventService;
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
//        createLectures();
//        createExams();
        createGroups();
//        createEvents();
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
        seminarService.saveSeminar(new Seminar("Business-Knigge", SCHLUESSELQUALIFIKATION));
        seminarService.saveSeminar(new Seminar("Case studies of Export business and Import business in Practice of industrial goods in/from non-EU", INTERNATIONALES));
        seminarService.saveSeminar(new Seminar("Der Business Plan - Mit System zum Erfolg", SCHLUESSELQUALIFIKATION));
        seminarService.saveSeminar(new Seminar("Einführung Wirtschaftsmediation", ETHIK_SOZIALES));
        seminarService.saveSeminar(new Seminar("Emotionale Intelligenz", ETHIK_SOZIALES));
        seminarService.saveSeminar(new Seminar("Excel VBA für Einsteiger", SCHLUESSELQUALIFIKATION));
        seminarService.saveSeminar(new Seminar("Große Dokumente in Word", SCHLUESSELQUALIFIKATION));
        seminarService.saveSeminar(new Seminar("Networking Excellence", SCHLUESSELQUALIFIKATION));
        seminarService.saveSeminar(new Seminar("Sourcecodeverwaltung mit Git und GitHub", SCHLUESSELQUALIFIKATION));
        seminarService.saveSeminar(new Seminar("Virtual Power Teams", INTERNATIONALES));
        seminarService.saveSeminar(new Seminar("Wirksame Tools für erfolgreiches Projektmanagement", SCHLUESSELQUALIFIKATION));
        seminarService.saveSeminar(new Seminar("Young Sales Professional Training", SONSTIGES));
        seminarService.saveSeminar(new Seminar("Zeit- und Selbstmanagement", SONSTIGES));
    }

//    private void createLectures() {
//        courseService.listCourses().forEach(course -> lectureService.saveLecture(new Lecture(15, course)));
//    }
//
//    private void createExams() {
//        courseService.listCourses().forEach(course -> examService.saveExam(new Exam(30, course)));
//    }

    private void createGroups() {
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

//    private void createEvents() {
//        Set<Room> eventRooms = new HashSet<>(Arrays.asList(roomService.findByBuildingAndNumber("A", "001")));
//        List<Docent> docents = docentService.listDocents();
//        Set<Docent> eventDocents = new HashSet<>();
//        docents.stream().filter(docent -> docent.getForename().contains("Uwe")).findFirst().ifPresent(eventDocents::add);
//        docents.stream().filter(docent -> docent.getForename().contains("Joachim")).findFirst().ifPresent(eventDocents::add);
//        Group eventGroup = centuryService.listCenturies().stream().filter(century -> century.getName().equals("I14a")).findFirst().get();
//        LocalDate date = LocalDate.of(2017, Month.DECEMBER, 12);
//        LocalTime startTime = LocalTime.of(9, 15);
//        LocalTime endTime = LocalTime.of(11, 30);
//        Course course = courseService.listCourses().stream().filter(c -> c.getField().equals("I") && c.getNumber() == 104).findFirst().get();
//        Subject eventSubject = lectureService.listLectures().stream().filter(lecture -> lecture.getCourse().equals(course)).findFirst().get();
//        Event event = new Event(eventRooms, eventDocents, eventGroup, date, startTime, endTime, eventSubject);
//        eventService.saveEvent(event);
//    }
}