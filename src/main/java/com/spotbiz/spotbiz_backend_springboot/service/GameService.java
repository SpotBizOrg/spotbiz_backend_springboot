package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.GameDto;
import com.spotbiz.spotbiz_backend_springboot.dto.UserDetailsDto;
import com.spotbiz.spotbiz_backend_springboot.dto.UserPointDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Game;
import com.spotbiz.spotbiz_backend_springboot.entity.GameType;
import com.spotbiz.spotbiz_backend_springboot.entity.User;

import java.util.List;
import java.util.Map;

public interface GameService {
    int insertGame(GameDto gameDto);
    List<GameDto> getAllGames(GameType gameType);
    GameDto updateGame(int gameId, GameDto gameDto);
    int deleteGame(int gameId);
    List<UserPointDto> getTopCustomers();
    UserDetailsDto getUserGameScores(int userId);
}


