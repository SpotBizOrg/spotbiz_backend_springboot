package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.ScannedCoupon;
import com.spotbiz.spotbiz_backend_springboot.entity.ScannedCouponStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ScannedCouponRepo extends JpaRepository<ScannedCoupon, Integer> {
     @Query("SELECT s FROM ScannedCoupon s WHERE CAST(s.businessId AS string) = :businessId AND s.status = :status")
     List<ScannedCoupon> findByBusinessIdAndStatus(@Param("businessId") int businessId, @Param("status") ScannedCouponStatus status);

     ScannedCoupon findByScannedCouponId(int id);
}
