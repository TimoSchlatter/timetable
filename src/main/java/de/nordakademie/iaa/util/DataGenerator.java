package de.nordakademie.iaa.util;

import de.nordakademie.iaa.model.Docent;
import de.nordakademie.iaa.model.Room;
import de.nordakademie.iaa.model.RoomType;
import de.nordakademie.iaa.service.DocentService;
import de.nordakademie.iaa.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;

@Component
public class DataGenerator {

    private DocentService docentService;
    private RoomService roomService;

    @Autowired
    public DataGenerator(DocentService docentService, RoomService roomService) {
        this.docentService = docentService;
        this.roomService = roomService;
    }

    @PostConstruct
    public void createData() {
        createRooms();
    }

    private void createRooms() {
        // A building
        roomService.saveRoom(new Room(20, "A", 40, "001", RoomType.COMPUTERROOM));
        roomService.saveRoom(new Room(15, "A", 40, "002", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(15, "A", 40, "003", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(15, "A", 40, "004", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(20, "A", 40, "101", RoomType.COMPUTERROOM));
        roomService.saveRoom(new Room(20, "A", 25, "102", RoomType.COMPUTERROOM));
        // D building
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
        docentService.saveDocent(new Docent("stefan.reichert@nordakademie.de", "Stefan", "Reichert", "04074735656", "", true, 30, new HashSet<>()));
        docentService.saveDocent(new Docent("uwe.neuhaus@nordakademie.de", "Uwe", "Neuhaus", "04074720656", "", true, 20, new HashSet<>()));
        docentService.saveDocent(new Docent("almut.lewe@nordakademie.de", "Almut", "Lewe", "04012320656", "", false, 45, new HashSet<>()));
        docentService.saveDocent(new Docent("joachim.sauer@nordakademie.de", "Joachim", "Sauer", "04018520656", "Prof. Dr.", true, 30, new HashSet<>()));
    }

    }
