package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SearchHistoryRepo extends JpaRepository<SearchHistory, Integer> {

    @Query("SELECT u.generatedKeywords FROM SearchHistory u WHERE u.userId = :userId ORDER BY u.updatedAt DESC")
    String [] findGeneratedKeywordsByUserIdOrderByUpdatedAtDesc(@Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM user_search_history 
        WHERE row_id NOT IN (
            SELECT row_id 
            FROM user_search_history 
            WHERE user_id = :userId 
            ORDER BY created_at DESC 
            LIMIT 10
        ) AND user_id = :userId
    """, nativeQuery = true)
    void deleteOlderRecordsForUser(@Param("userId") Integer userId);
}
