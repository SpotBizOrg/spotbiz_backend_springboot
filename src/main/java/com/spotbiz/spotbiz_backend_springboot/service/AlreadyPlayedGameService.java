package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.entity.AlreadyPlayedGame;
import java.util.List;

public interface AlreadyPlayedGameService {
    int insertAlreadyPlayedGame(AlreadyPlayedGame alreadyPlayedGame);
    List<AlreadyPlayedGame> getAlreadyPlayedGames(int userId);
}
