package de.nordakademie.iaa.util;

import de.nordakademie.iaa.model.Room;
import de.nordakademie.iaa.model.RoomType;
import de.nordakademie.iaa.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;

@Component
public class DataGenerator {

    private RoomService roomService;

    @Autowired
    public DataGenerator(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostConstruct
    public void createData() {
        createRooms();
    }

    private void createRooms() {
        // A building
        roomService.saveRoom(new Room(Duration.ofMinutes(20), "A", 40, "001", RoomType.COMPUTERROOM));
        roomService.saveRoom(new Room(Duration.ofMinutes(15), "A", 40, "002", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(Duration.ofMinutes(15), "A", 40, "003", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(Duration.ofMinutes(15), "A", 40, "004", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(Duration.ofMinutes(20), "A", 40, "101", RoomType.COMPUTERROOM));
        roomService.saveRoom(new Room(Duration.ofMinutes(20), "A", 25, "102", RoomType.COMPUTERROOM));
        // D building
        roomService.saveRoom(new Room(Duration.ofMinutes(15), "D", 20, "001", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(Duration.ofMinutes(15), "D", 20, "002", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(Duration.ofMinutes(15), "D", 20, "003", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(Duration.ofMinutes(15), "D", 20, "004", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(Duration.ofMinutes(30), "D", 20, "005", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(Duration.ofMinutes(15), "D", 20, "101", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(Duration.ofMinutes(15), "D", 20, "102", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(Duration.ofMinutes(15), "D", 20, "103", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(Duration.ofMinutes(15), "D", 20, "104", RoomType.LECTUREROOM));
        roomService.saveRoom(new Room(Duration.ofMinutes(30), "D", 20, "105", RoomType.LECTUREROOM));
    }

}
