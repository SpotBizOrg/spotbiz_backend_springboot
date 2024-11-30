package com.spotbiz.spotbiz_backend_springboot.service;

import com.spotbiz.spotbiz_backend_springboot.entity.Reimbursements;
import com.spotbiz.spotbiz_backend_springboot.entity.ScannedCoupon;
import com.spotbiz.spotbiz_backend_springboot.entity.ScannedCouponStatus;

import java.util.List;

public interface ScannedCouponService {
    int insertScannedCoupon(ScannedCoupon scannedCoupon);
    List<ScannedCoupon> getScannedCouponsForBusiness(int businessId);
    Reimbursements payScannedCouponAmount(List<ScannedCoupon> scannedCoupons);
    float findAmountById(int id);
    void changeStatus(int id, ScannedCouponStatus status);
}
