package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.entity.*;
import com.spotbiz.spotbiz_backend_springboot.repo.EncodedCouponRepo;
import com.spotbiz.spotbiz_backend_springboot.service.CouponService;
import com.spotbiz.spotbiz_backend_springboot.service.EncodedCouponService;
import com.spotbiz.spotbiz_backend_springboot.util.EncryptionUtil;
import org.springframework.stereotype.Service;

@Service
public class EncodedCouponServiceImpl implements EncodedCouponService {

    private final EncodedCouponRepo encodedCouponRepo;
    private final CouponService couponService;

    public EncodedCouponServiceImpl(EncodedCouponRepo encodedCouponRepo, CouponService couponService) {
        this.encodedCouponRepo = encodedCouponRepo;
        this.couponService = couponService;
    }

    @Override
    public String insertEncodedCoupon(Coupon coupon) {
        try{
            String encryptedCouponId = EncryptionUtil.encodeCouponId(String.valueOf(coupon.getCouponId()));
            EncodedCoupon encodedCoupon = new EncodedCoupon();
            encodedCoupon.setCoupon_id(coupon.getCouponId());
            encodedCoupon.setDiscount(coupon.getDiscount());
            encodedCoupon.setEncodedCouponId(encryptedCouponId);
            encodedCoupon.setStatus(coupon.getStatus());
            encodedCouponRepo.save(encodedCoupon);
            return encryptedCouponId;
        }
        catch (Exception e){
            throw new RuntimeException("Error in inserting encoded coupon");
        }
    }

    @Override
    public EncodedCoupon getEncodedCouponById(String encodedCouponId) {
        try {
            EncodedCoupon encodedCoupon = encodedCouponRepo.findByEncodedCouponId(encodedCouponId);
            CouponStatus status = couponService.findByCouponId(encodedCoupon.getCoupon_id()).getStatus();
            if (status != null) {
                encodedCoupon.setStatus(status);
                return encodedCoupon;
            } else {
                throw new RuntimeException("Coupon not found for the given encoded coupon ID");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error in getting encoded coupon by ID", e);
        }
    }

}
