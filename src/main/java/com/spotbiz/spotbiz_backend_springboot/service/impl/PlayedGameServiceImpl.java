package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.PlayedGameDto;
import com.spotbiz.spotbiz_backend_springboot.entity.PlayedGames;
import com.spotbiz.spotbiz_backend_springboot.mapper.PlayedGameMapper;
import com.spotbiz.spotbiz_backend_springboot.repo.PlayedGameRepo;
import com.spotbiz.spotbiz_backend_springboot.service.PlayedGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayedGameServiceImpl implements PlayedGameService {

    private final PlayedGameRepo playedGameRepo;
    private final PlayedGameMapper playedGameMapper;

    @Autowired
    public PlayedGameServiceImpl(PlayedGameRepo playedGameRepo, PlayedGameMapper playedGameMapper) {
        this.playedGameRepo = playedGameRepo;
        this.playedGameMapper = playedGameMapper;
    }

    @Override
    public int insertPlayedGame(PlayedGameDto gamePlayDto) {
        System.out.println("gamePlayDto: " + gamePlayDto);

        try {
            PlayedGames playedGames = playedGameMapper.toPlayedGames(gamePlayDto);
            System.out.println("Mapped PlayedGames: " + playedGames);

            playedGames = playedGameRepo.save(playedGames);
            System.out.println("Saved PlayedGames: " + playedGames);

            return playedGames.getPlayId();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


}
