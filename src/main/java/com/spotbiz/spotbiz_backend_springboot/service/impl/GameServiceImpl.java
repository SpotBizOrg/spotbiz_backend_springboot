package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.GameDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Game;
import com.spotbiz.spotbiz_backend_springboot.entity.GameType;
import com.spotbiz.spotbiz_backend_springboot.mapper.GameMapper;
import com.spotbiz.spotbiz_backend_springboot.repo.GameRepo;
import com.spotbiz.spotbiz_backend_springboot.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepo gameRepo;
    private final GameMapper gameMapper;

    @Autowired
    public GameServiceImpl(GameRepo gameRepo, GameMapper gameMapper) {
        this.gameRepo = gameRepo;
        this.gameMapper = gameMapper;
    }

    @Override
    public int insertGame(GameDto gameDto) {

        try {
            Game game = gameMapper.toGame(gameDto);
            game = gameRepo.save(game);

            return game.getGameId();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<GameDto> getAllGames(GameType gameType) {
        List<Game> games = gameRepo.findByGameType(gameType);
        List<GameDto> gameDtos = new ArrayList<>();

        for (Game game : games) {
            GameDto gameDto = new GameDto();
            gameDto.setGameName(game.getGameName());
            gameDto.setGameType(game.getGameType());
            gameDto.setDeveloper(game.getDeveloper());
            gameDto.setDescription(game.getDescription());
            gameDto.setGameUrl(game.getGameUrl());

            // Load the image file and convert it to byte array
            String imagePath = "uploads/game_banners/game_" + game.getGameId() + ".jpg";
            try {
                Path path = Paths.get(imagePath);
                byte[] imageData = Files.readAllBytes(path);
                gameDto.setImageData(imageData);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the error, possibly set a default image or log the error
            }

            gameDtos.add(gameDto);
        }

        return gameDtos;
    }


}
