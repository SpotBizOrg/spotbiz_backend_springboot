package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.dto.UserPointDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Game;
import com.spotbiz.spotbiz_backend_springboot.entity.GameType;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface GameRepo extends JpaRepository<Game, Integer> {
    List<Game> findByGameType(GameType gameType);
    Game findByGameId(int gameId);

    @Query("SELECT new com.spotbiz.spotbiz_backend_springboot.dto.UserPointDto(u.userId, u.name, u.email, u.phoneNo, u.status, u.role, SUM(pg.points)) " +
            "FROM PlayedGames pg " +
            "JOIN User u ON u.userId = pg.userId " +
            "WHERE pg.dateTime >= :startDate " +
            "GROUP BY u.userId " +
            "ORDER BY SUM(pg.points) DESC")
    List<UserPointDto> findTopUsersByPointsWithin30Days(@Param("startDate") LocalDateTime startDate, Pageable pageable);
}
