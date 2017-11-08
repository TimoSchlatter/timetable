package model;

import de.nordakademie.iaa.model.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EventTest {

    private final Room room1 = new Room(15, "A", 40, "001", RoomType.LECTUREROOM);
    private final Room room2 = new Room(20, "A", 30, "002", RoomType.COMPUTERROOM);
    private final Docent docent1 = new Docent("q@q.com", "Eddy", "Bruh", "0115563487", "Dr. habil.", true, 20);
    private final Docent docent2 = new Docent("a@a.com", "Ed", "Sy", "0115273487", "Dr.", true, 40);
    private final Group group = new Cohort("t",20);

    private final Event event = new Event();

    @Test
    public void testRemoveRoom() {
        event.setRooms(new HashSet<>(Arrays.asList(room1, room2)));
        assertEquals(2, event.getRooms().size());
        event.removeRoom(room1);
        assertEquals(1, event.getRooms().size());
        assertTrue(event.getRooms().contains(room2));
        event.removeRoom(room2);
        assertTrue(event.getRooms().isEmpty());
    }

    @Test
    public void testRemoveDocent() {
        event.setDocents(new HashSet<>(Arrays.asList(docent1, docent2)));
        assertEquals(2, event.getDocents().size());
        event.removeDocent(docent1);
        assertEquals(1, event.getDocents().size());
        assertTrue(event.getDocents().contains(docent2));
        event.removeDocent(docent2);
        assertTrue(event.getDocents().isEmpty());
    }

    @Test
    public void testCalculateMinChangeoverTime() {
        event.setRooms(new HashSet<>(Arrays.asList(room1, room2)));
        event.setDocents(new HashSet<>(Arrays.asList(docent1, docent2)));
        event.setGroup(group);
        assertEquals(event.calculateMinChangeoverTime(), 40);
    }
}
