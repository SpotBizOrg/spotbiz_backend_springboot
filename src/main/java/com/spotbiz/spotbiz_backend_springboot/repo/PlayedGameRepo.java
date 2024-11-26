package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.dto.CustomerAdminResponseDto;
import com.spotbiz.spotbiz_backend_springboot.entity.PlayedGames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface PlayedGameRepo extends JpaRepository<PlayedGames, Integer> {

    @Query(value = "SELECT COALESCE((SELECT sum(g.points) FROM played_games g WHERE g.user_id = :userId GROUP BY g.user_id), 0.0) AS score", nativeQuery = true)
    double getCustomerScore(@Param("userId") Integer userId);

}
