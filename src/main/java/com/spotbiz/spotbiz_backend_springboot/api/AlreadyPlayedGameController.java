package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.entity.AlreadyPlayedGame;
import com.spotbiz.spotbiz_backend_springboot.repo.GameRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.UserRepo;
import com.spotbiz.spotbiz_backend_springboot.service.AlreadyPlayedGameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/already_played_games")
public class AlreadyPlayedGameController {

    private final AlreadyPlayedGameService alreadyPlayedGameService;
    private final UserRepo userRepo;
    private final GameRepo gameRepo;

    public AlreadyPlayedGameController(AlreadyPlayedGameService alreadyPlayedGameService, UserRepo userRepo, GameRepo gameRepo) {
        this.alreadyPlayedGameService = alreadyPlayedGameService;
        this.userRepo = userRepo;
        this.gameRepo = gameRepo;
    }

    @PostMapping("/{userId}/{gameId}")
    public ResponseEntity<?> insertAlreadyPlayedGame(@PathVariable int userId, @PathVariable int gameId) {
        try {
            AlreadyPlayedGame alreadyPlayedGame = new AlreadyPlayedGame();
            alreadyPlayedGame.setUser(userRepo.findByUserId(userId));
            alreadyPlayedGame.setGame(gameRepo.findByGameId(gameId));
            int alreadyPlayedGameId = alreadyPlayedGameService.insertAlreadyPlayedGame(alreadyPlayedGame);

            if (alreadyPlayedGameId > 0) {
                return ResponseEntity.ok(alreadyPlayedGameId);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to insert played game");
            }
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAlreadyPlayedGames(@PathVariable int id) {
        try {
            List<AlreadyPlayedGame> alreadyPlayedGames = alreadyPlayedGameService.getAlreadyPlayedGames(id);
            System.out.println(alreadyPlayedGames.size());
            if(alreadyPlayedGames.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No games found");
            }
            return ResponseEntity.ok(alreadyPlayedGames);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
