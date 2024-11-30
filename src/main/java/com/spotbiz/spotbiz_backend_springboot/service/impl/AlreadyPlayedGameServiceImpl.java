package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.GameDto;
import com.spotbiz.spotbiz_backend_springboot.dto.UserPointDto;
import com.spotbiz.spotbiz_backend_springboot.entity.AlreadyPlayedGame;
import com.spotbiz.spotbiz_backend_springboot.entity.Game;
import com.spotbiz.spotbiz_backend_springboot.entity.GameType;
import com.spotbiz.spotbiz_backend_springboot.mapper.GameMapper;
import com.spotbiz.spotbiz_backend_springboot.repo.AlreadyPlayedGameRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.GameRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.UserRepo;
import com.spotbiz.spotbiz_backend_springboot.service.AlreadyPlayedGameService;
import com.spotbiz.spotbiz_backend_springboot.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlreadyPlayedGameServiceImpl implements AlreadyPlayedGameService {

    private final AlreadyPlayedGameRepo alreadyPlayedgameRepo;
    private final UserRepo userRepo;

    @Autowired
    public AlreadyPlayedGameServiceImpl(AlreadyPlayedGameRepo alreadyPlayedgameRepo, UserRepo userRepo) {
        this.alreadyPlayedgameRepo = alreadyPlayedgameRepo;
        this.userRepo = userRepo;
    }

    @Override
    public int insertAlreadyPlayedGame(AlreadyPlayedGame alreadyPlayedGame) {
        try {
            return (alreadyPlayedgameRepo.save(alreadyPlayedGame)).getId();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<AlreadyPlayedGame> getAlreadyPlayedGames(int userId) {
        return alreadyPlayedgameRepo.findUniqueGamesByUser(userRepo.findByUserId(userId));
    }
}
