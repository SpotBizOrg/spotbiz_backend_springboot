package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchHistoryRepo extends JpaRepository<SearchHistory, Integer> {

    @Query("SELECT u.generatedKeywords FROM SearchHistory u WHERE u.userId = :userId ORDER BY u.updatedAt DESC")
    List<String> findGeneratedKeywordsByUserIdOrderByUpdatedAtDesc(@Param("userId") Integer userId);
}
