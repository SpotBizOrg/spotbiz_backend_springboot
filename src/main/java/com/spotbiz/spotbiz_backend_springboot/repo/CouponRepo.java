package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface CouponRepo extends JpaRepository<Coupon, Integer> {
    Coupon findByCouponId(int coupon_id);
    List<Coupon> findAllByOrderByCouponIdAsc();
}
