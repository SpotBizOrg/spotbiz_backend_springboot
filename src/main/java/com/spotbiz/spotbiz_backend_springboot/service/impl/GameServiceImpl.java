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
            GameDto gameDto = gameMapper.toGameDto(game);
            gameDtos.add(gameDto);
        }

        return gameDtos;
    }


}
