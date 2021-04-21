package chess.controller.spring;

import chess.domain.room.Room;
import chess.dto.RoomDTO;
import chess.dto.RoomRegistrationDTO;
import chess.service.spring.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        List<RoomDTO> roomDTOS = roomService.findAllRooms()
                .stream()
                .map(RoomDTO::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(roomDTOS);
    }

    @PostMapping
    public ResponseEntity<RoomDTO> addRoom(@RequestBody RoomRegistrationDTO roomRegistrationDTO, HttpSession httpSession, HttpServletResponse httpServletResponse) {
        httpSession.setAttribute("password", roomRegistrationDTO.getPassword());
        Cookie cookie = new Cookie("password", roomRegistrationDTO.getPassword());
        httpServletResponse.addCookie(cookie);
        roomService.addRoom(roomRegistrationDTO.getName());
        Room room = roomService.findLastAddedRoom();
        RoomDTO roomDTO = RoomDTO.from(room);
        return ResponseEntity.ok().body(roomDTO);
    }
}
