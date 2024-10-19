package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.GameDto;
import com.spotbiz.spotbiz_backend_springboot.entity.GameType;
import com.spotbiz.spotbiz_backend_springboot.service.GameService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/all_games/{gameType}")
    public ResponseEntity<?> getAllGames(@PathVariable GameType gameType) {
        try {
            List<GameDto> allGames = gameService.getAllGames(gameType);
            if(allGames.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No games found");
            }
            return ResponseEntity.ok(allGames);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/update_game")
    public ResponseEntity<?> updateGame(@RequestBody GameDto gameDto) {
        try {
            int gameId = gameDto.getGameId();
            GameDto updatedGame = gameService.updateGame(gameId, gameDto);
            return ResponseEntity.ok(updatedGame);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game not found: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }


    @DeleteMapping("/delete_game/{gameId}")
    public ResponseEntity<?> deleteGame(@PathVariable int gameId) {
        try {
            int deletedGame = gameService.deleteGame(gameId);
            if (deletedGame > 0) {
                return ResponseEntity.ok(deletedGame);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(deletedGame);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

}
