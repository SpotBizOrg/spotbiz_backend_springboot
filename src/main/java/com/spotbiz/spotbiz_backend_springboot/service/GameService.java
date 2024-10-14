package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.GameDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Game;
import com.spotbiz.spotbiz_backend_springboot.entity.GameType;

import java.util.List;

public interface GameService {
    int insertGame(GameDto gameDto);
    List<GameDto> getAllGames(GameType gameType);
}
