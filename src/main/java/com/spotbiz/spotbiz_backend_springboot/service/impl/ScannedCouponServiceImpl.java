package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.entity.Coupon;
import com.spotbiz.spotbiz_backend_springboot.entity.CouponStatus;
import com.spotbiz.spotbiz_backend_springboot.entity.Reimbursements;
import com.spotbiz.spotbiz_backend_springboot.entity.ScannedCoupon;
import com.spotbiz.spotbiz_backend_springboot.repo.CouponRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.ScannedCouponRepo;
import com.spotbiz.spotbiz_backend_springboot.service.ScannedCouponService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScannedCouponServiceImpl implements ScannedCouponService {

    private final ScannedCouponRepo scannedCouponRepo;
    private final CouponRepo couponRepo;

    public ScannedCouponServiceImpl(ScannedCouponRepo scannedCouponRepo, CouponRepo couponRepo) {
        this.scannedCouponRepo = scannedCouponRepo;
        this.couponRepo = couponRepo;
    }

    @Override
    public int insertScannedCoupon(ScannedCoupon scannedCoupon) {
        if(scannedCoupon.getCouponId() <= 0 || scannedCoupon.getDateTime() == null || scannedCoupon.getBusinessId() <= 0 || scannedCoupon.getDiscount() <= 0) {
            throw new RuntimeException("Request is incomplete");
        }
        if(couponRepo.findByCouponId(scannedCoupon.getCouponId()) == null) {
            throw new RuntimeException("Coupon not found");
        }

        Coupon coupon = couponRepo.findByCouponId(scannedCoupon.getCouponId());

        if(coupon == null){
            throw new RuntimeException("Coupon not found");
        }

        if(coupon.getStatus() == CouponStatus.PENDING || coupon.getStatus() == CouponStatus.USED) {
            throw new RuntimeException("Coupon not valid");
        }

        ScannedCoupon savedScannedCoupon = scannedCouponRepo.save(scannedCoupon);

        if(savedScannedCoupon.getScannedCouponId() <= 0) {
            throw new RuntimeException("Insertion failed");
        }

        coupon.setStatus(CouponStatus.USED);

        if(couponRepo.save(coupon).getCouponId() <= 0) {
            throw new RuntimeException("Update coupon status failed");
        }

        return 1;
    }

    @Override
    public List<ScannedCoupon> getScannedCouponsForBusiness(int businessId) {
        if(businessId <= 0) {
            throw new RuntimeException("Business id is null");
        }
        return scannedCouponRepo.findByBusinessId(businessId);
    }

    @Override
    public Reimbursements payScannedCouponAmount(List<ScannedCoupon> scannedCoupons) {
        return null;
    }


}
