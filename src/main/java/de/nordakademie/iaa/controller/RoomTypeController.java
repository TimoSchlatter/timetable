package de.nordakademie.iaa.controller;

import de.nordakademie.iaa.model.RoomType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequestMapping("/roomtypes")
public class RoomTypeController {

    @GetMapping
    public RoomType[] listRoomTypes() {
        return RoomType.values();
    }
}