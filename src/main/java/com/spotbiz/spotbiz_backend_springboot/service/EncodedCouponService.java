package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.entity.Coupon;
import com.spotbiz.spotbiz_backend_springboot.entity.EncodedCoupon;

public interface EncodedCouponService {
    String insertEncodedCoupon(Coupon coupon);
    EncodedCoupon getEncodedCouponById(String encodedCouponId);
}
