package de.nordakademie.iaa.service;

import de.nordakademie.iaa.dao.RoomDAO;
import de.nordakademie.iaa.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomDAO roomDAO;

    @Override
    public void saveRoom(Room room) {
        roomDAO.save(room);
    }

    public List<Room> listRooms() {
        return roomDAO.findAll();
    }

    @Override
    public Room loadRoom(Long id) {
        return roomDAO.findOne(id);
    }

    @Override
    public void deleteRoom(Long id) throws EntityNotFoundException {
        Room room = loadRoom(id);
        if (room == null) {
            throw new EntityNotFoundException();
        }
        roomDAO.delete(room);
    }
}
