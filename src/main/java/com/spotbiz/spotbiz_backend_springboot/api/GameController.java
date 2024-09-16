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

    @PostMapping(value = "/insert_game", consumes = "multipart/form-data")
    public ResponseEntity<?> insertGame(
            @RequestParam("gameName") String gameName,
            @RequestParam("gameType") GameType gameType,
            @RequestParam("developer") String developer,
            @RequestParam("description") String description,
            @RequestParam("gameUrl") String gameUrl,
            @RequestParam("gameImg") MultipartFile gameImg) {

        try {
            GameDto gameDto = new GameDto();
            gameDto.setGameName(gameName);
            gameDto.setGameType(gameType);
            gameDto.setDeveloper(developer);
            gameDto.setDescription(description);
            gameDto.setGameUrl(gameUrl);

            int gameId = gameService.insertGame(gameDto);

            if (gameId > 0 && !gameImg.isEmpty()) {
                String projectDir = System.getProperty("user.dir");
                String uploadDir = projectDir + File.separator + "uploads" + File.separator + "game_banners";

                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    boolean dirCreated = dir.mkdirs();
                    if (!dirCreated) {
                        throw new RuntimeException("Failed to create directory: " + dir.getAbsolutePath());
                    }
                }

                String fileExtension = getFileExtension(gameImg.getOriginalFilename());
                String filename = "game_" + gameId + "." + fileExtension;
                File dest = new File(dir, filename);
                System.out.println(dest.getAbsolutePath());

                try {
                    gameImg.transferTo(dest);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Error saving the image file", e);
                }
            }

            return ResponseEntity.ok(gameId);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    private String getFileExtension(String filename) {
        if (filename == null) {
            return "";
        }
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex + 1);
    }

    @GetMapping("/all_games/{gameType}")
    public ResponseEntity<?> getNormalGame(@PathVariable GameType gameType) {
        try {
            List<GameDto> allGames = gameService.getAllGames(gameType);
            return ResponseEntity.ok(allGames);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }


}
