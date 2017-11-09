package de.nordakademie.iaa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.nordakademie.iaa.model.RoomType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * REST-Controller for room types.
 *
 * @author Timo Schlatter
 */
@Transactional
@RestController
@RequestMapping("/roomtypes")
public class RoomTypeController {

    /**
     * Lists all room types.
     *
     * @return the list of all room types.
     */
    @GetMapping
    public String listRoomTypes() throws Exception {
        Map<RoomType, String> roomTypes = new HashMap<>();
        Arrays.stream(RoomType.values()).forEach(roomType -> roomTypes.put(roomType, roomType.getTranslation()));
        return new ObjectMapper().writeValueAsString(roomTypes);
    }
}