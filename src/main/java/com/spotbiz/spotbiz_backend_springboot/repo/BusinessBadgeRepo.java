package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.BusinessBadge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessBadgeRepo extends JpaRepository<BusinessBadge, Integer> {

    @Query(value = "SELECT r.business_id, \n" +
            "       AVG(r.rating) AS avg_rating, \n" +
            "       bc.clicks\n" +
            "FROM review r\n" +
            "JOIN business_clicks bc ON r.business_id = bc.business_id\n" +
            "WHERE r.date >= NOW() - INTERVAL '60 DAY'\n" +
            "GROUP BY r.business_id, bc.clicks\n" +
            "ORDER BY avg_rating DESC, bc.clicks DESC\n" +
            "LIMIT 1", nativeQuery = true)
    List<Object[]> getBusinessForBadge();

    List<BusinessBadge> findBusinessBadgesByIssuedDateBetweenOrderByIssuedDateAsc(LocalDateTime sixMonthsAgo, LocalDateTime now);

    @Query(value = "SELECT * FROM business_badge WHERE business_id =:businessId ORDER BY issued_date DESC LIMIT 1", nativeQuery = true)
    Optional<BusinessBadge> getLatestBadge(@Param("businessId") Integer businessId);
}
