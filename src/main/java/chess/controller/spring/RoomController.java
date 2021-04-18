package chess.controller.spring;

import chess.domain.room.Room;
import chess.dto.RoomDTO;
import chess.service.spring.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<RoomDTO>> showRooms() {
        List<RoomDTO> allRooms = roomService.findAllRooms()
                .stream()
                .map(RoomDTO::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(allRooms);
    }

    @PostMapping
    public ResponseEntity<RoomDTO> addRoom(@RequestBody String name) {
        roomService.addRoom(name);
        Room room = roomService.findLastRoom();
        RoomDTO roomDTO = RoomDTO.from(room);
        return ResponseEntity.ok().body(roomDTO);
    }
}