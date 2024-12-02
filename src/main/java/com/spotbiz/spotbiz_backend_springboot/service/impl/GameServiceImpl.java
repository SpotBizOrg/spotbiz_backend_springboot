package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.GameDto;
import com.spotbiz.spotbiz_backend_springboot.dto.UserPointDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Game;
import com.spotbiz.spotbiz_backend_springboot.entity.GameType;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import com.spotbiz.spotbiz_backend_springboot.mapper.GameMapper;
import com.spotbiz.spotbiz_backend_springboot.repo.GameRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.UserRepo;
import com.spotbiz.spotbiz_backend_springboot.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepo gameRepo;
    private final GameMapper gameMapper;
    private final UserRepo userRepo;

    @Autowired
    public GameServiceImpl(GameRepo gameRepo, GameMapper gameMapper, UserRepo userRepo) {
        this.gameRepo = gameRepo;
        this.gameMapper = gameMapper;
        this.userRepo = userRepo;
    }

    @Override
    public int insertGame(GameDto gameDto) {

        try {
            Game game = gameMapper.toGame(gameDto);
            System.out.println(game.getGameId());
            System.out.println(game.getGameName());
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

    @Override
    public GameDto updateGame(int gameId, GameDto gameDto) {
        System.out.println(gameDto.getDescription());
        if(gameDto.getGameId() == gameId){
            Game game = gameRepo.findByGameId(gameId);
            if(game != null){
                if (gameDto.getGameType() != null && !gameDto.getGameType().equals(game.getGameType())) {
                    game.setGameType(gameDto.getGameType());
                }
                if (gameDto.getGameUrl() != null && !gameDto.getGameUrl().isEmpty() && !gameDto.getGameUrl().equals(game.getGameUrl())) {
                    game.setGameUrl(gameDto.getGameUrl());
                }
                if (gameDto.getGameName() != null && !gameDto.getGameName().isEmpty() && !gameDto.getGameName().equals(game.getGameName())) {
                    game.setGameName(gameDto.getGameName());
                }
                if (gameDto.getDescription() != null && !gameDto.getDescription().isEmpty() && !gameDto.getDescription().equals(game.getDescription())) {
                    game.setDescription(gameDto.getDescription());
                }
                if (gameDto.getDeveloper() != null && !gameDto.getDeveloper().isEmpty() && !gameDto.getDeveloper().equals(game.getDeveloper())) {
                    game.setDeveloper(gameDto.getDeveloper());
                }
                if (gameDto.getImageUrl() != null && !gameDto.getImageUrl().isEmpty() && !gameDto.getImageUrl().equals(game.getImageUrl())) {
                    game.setImageUrl(gameDto.getImageUrl());
                }
                game = gameRepo.save(game);
                return gameMapper.toGameDto(gameRepo.save(game));
            }
        }
        return null;
    }

    @Override
    public int deleteGame(int gameId) {
        Game game = gameRepo.findByGameId(gameId);
        gameRepo.delete(game);
        if(gameRepo.findByGameId(gameId) != null){
            return 0;
        }
        return 1;
    }

    @Override
    public List<UserPointDto> getTopCustomers() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        Pageable topTen = PageRequest.of(0, 10);
        return gameRepo.findTopUsersByPointsWithin30Days(startDate, topTen);
    }

    @Override
    public UserPointDto getUserGameScores(int userId) {
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        try {
            Optional<User> userOpt =  userRepo.findById(userId);
            if (userOpt.isPresent()){
                User user = userOpt.get();

                Double totalPoints = gameRepo.findTotalPointsByPlayerIdWithin30Days(userId, startDate);
                if (totalPoints != null) {
                    return new UserPointDto(user.getUserId(), user.getName(), user.getEmail(), user.getPhoneNo(), user.getStatus(), user.getRole(), totalPoints);
                }
                return new UserPointDto(user.getUserId(), user.getName(), user.getEmail(), user.getPhoneNo(), user.getStatus(), user.getRole(), 0);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get user game scores");
        }
    }


}
