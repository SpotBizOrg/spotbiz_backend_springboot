package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.AlreadyPlayedGame;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface AlreadyPlayedGameRepo extends JpaRepository<AlreadyPlayedGame, Integer> {
    @Query("SELECT apg FROM AlreadyPlayedGame apg " +
            "WHERE apg.user = :user " +
            "AND apg.id = (SELECT MAX(apg2.id) FROM AlreadyPlayedGame apg2 WHERE apg2.game = apg.game AND apg2.user = :user) " +
            "ORDER BY apg.id DESC")
    List<AlreadyPlayedGame> findUniqueGamesByUser(@Param("user") User user);
}
