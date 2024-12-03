package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.dto.ReviewRequestDto;
import com.spotbiz.spotbiz_backend_springboot.dto.ReviewStatsDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.Review;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;


@EnableJpaRepositories
@Repository
public interface ReviewRepo extends JpaRepository<Review, Integer> {
    boolean existsByUserAndBusiness(User user, Business business);

    @Query(value = "SELECT COALESCE((SELECT AVG(r.rating) FROM review r WHERE r.business_id = :businessId), 0.0) AS avg_rating", nativeQuery = true)
    double getAverageRatingByBusiness(@Param("businessId") Integer businessId);

    @Query(value = "SELECT COALESCE((SELECT COUNT(r.review_id) FROM review r WHERE r.business_id = :businessId GROUP BY business_id), 0) AS review_count", nativeQuery = true)
    int countByBusiness(@Param("businessId") Integer businessId);

    @Query(value = "SELECT * FROM review r WHERE r.business_id = :businessId ORDER BY r.date DESC LIMIT 1", nativeQuery = true)
    Optional<Review> findLatestBusinessReview(@Param("businessId") Integer businessId);

    List<Review> findReviewsByStatus(String status);

    List<Review> findByBusiness(Business business);

    @Query("SELECT new com.spotbiz.spotbiz_backend_springboot.dto.ReviewStatsDto(" +
            "AVG(r.rating), " +
            "SUM(CASE WHEN r.rating = 1 THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN r.rating = 2 THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN r.rating = 3 THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN r.rating = 4 THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN r.rating = 5 THEN 1 ELSE 0 END), " +
            "COUNT(r)) " +
            "FROM Review r WHERE r.business.businessId = :businessId")
    ReviewStatsDto getRatingStatistics(int businessId);

}
