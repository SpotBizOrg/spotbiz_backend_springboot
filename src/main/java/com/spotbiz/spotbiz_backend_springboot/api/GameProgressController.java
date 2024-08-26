package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.PlayedGameDto;
import com.spotbiz.spotbiz_backend_springboot.service.PlayedGameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/game_progress")
public class GameProgressController {

    private final PlayedGameService playedGameService;

    public GameProgressController(PlayedGameService playedGameService) {
        this.playedGameService = playedGameService;
    }

    @PostMapping("/save_progress")
    public ResponseEntity<?> saveProgress(@RequestBody PlayedGameDto gamePlayDto) {
        try {
            int insertSuccess = playedGameService.insertPlayedGame(gamePlayDto);
            return ResponseEntity.ok(insertSuccess);
        } catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

}
