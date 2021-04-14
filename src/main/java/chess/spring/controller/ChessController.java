package chess.spring.controller;

import chess.domain.board.ChessBoard;
import chess.domain.piece.TeamType;
import chess.domain.result.Result;
import chess.dto.MoveRequestDTO;
import chess.dto.ResultDTO;
import chess.dto.board.BoardDTO;
import chess.spring.service.ChessService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chessgame")
public class ChessController {

    private final ChessService chessService;

    public ChessController(ChessService chessService) {
        this.chessService = chessService;
    }

    @GetMapping("/show")
    public BoardDTO showChessBoard() {
        return findChessBoard();
    }

    private BoardDTO findChessBoard() {
        ChessBoard chessBoard = chessService.findChessBoard();
        TeamType teamType = chessService.findCurrentTeamType();
        return BoardDTO.of(chessBoard, teamType);
    }

    @PostMapping(path = "/move")
    public BoardDTO move(@RequestBody MoveRequestDTO moveRequestDTO) {
        String current = moveRequestDTO.getCurrent();
        String destination = moveRequestDTO.getDestination();
        String teamType = moveRequestDTO.getTeamType();
        chessService.move(current, destination, teamType);
        return findChessBoard();
    }

    @GetMapping("/show/result")
    public ResultDTO showResult() {
        Result result = chessService.calculateResult();
        return ResultDTO.from(result);
    }

    @GetMapping("/restart")
    public String restart() {
        chessService.deleteAllHistories();
        return "/";
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(RuntimeException runtimeException) {
        return ResponseEntity.status(500).body(runtimeException.getMessage());
    }
}