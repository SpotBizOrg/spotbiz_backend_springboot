package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.dto.CouponDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Coupon;
import com.spotbiz.spotbiz_backend_springboot.entity.CouponStatus;

import java.util.List;

public interface CouponService {
    int insertCoupon(CouponDto couponDto);
    int issueCoupon(int user_id, int couponId);
    int useCoupon(int businessId, int couponId);
    int updateCoupon(CouponDto couponDto, int couponId);
    CouponStatus checkCoupon(int couponId);
    int deleteCoupon(int couponId);
    List<Coupon> getAllCouponDetails();
    Coupon findByCouponId(int couponId);
}
