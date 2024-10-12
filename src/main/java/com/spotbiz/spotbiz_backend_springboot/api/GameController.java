package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.GameDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Game;
import com.spotbiz.spotbiz_backend_springboot.entity.GameType;
import com.spotbiz.spotbiz_backend_springboot.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping(value = "/insert_game", consumes = "application/json")
    public ResponseEntity<?> insertGame(@RequestBody GameDto gameDto) {
        try {
            System.out.println(gameDto.getImageUrl() + " " + gameDto.getGameType());
            int gameId = gameService.insertGame(gameDto);

            if (gameId > 0) {
                return ResponseEntity.ok(gameId);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to insert game");
            }
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @GetMapping("/all_games/{gameType}")
    public ResponseEntity<?> getAllGames(@PathVariable GameType gameType) {
        try {
            List<GameDto> allGames = gameService.getAllGames(gameType);
            return ResponseEntity.ok(allGames);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }


}
