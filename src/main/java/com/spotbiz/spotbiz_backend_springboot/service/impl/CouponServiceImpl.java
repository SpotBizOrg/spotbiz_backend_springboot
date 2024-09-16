package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.CouponDto;
import com.spotbiz.spotbiz_backend_springboot.entity.*;
import com.spotbiz.spotbiz_backend_springboot.mapper.CouponMapper;
import com.spotbiz.spotbiz_backend_springboot.repo.BusinessRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.CouponRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.UserRepo;
import com.spotbiz.spotbiz_backend_springboot.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepo couponRepo;
    private final UserRepo userRepo;
    private final CouponMapper couponMapper;
    private final BusinessRepo businessRepo;

    @Autowired
    public CouponServiceImpl(CouponRepo couponRepo, UserRepo userRepo, CouponMapper couponMapper, BusinessRepo businessRepo) {

        this.couponRepo = couponRepo;
        this.userRepo = userRepo;
        this.couponMapper = couponMapper;
        this.businessRepo = businessRepo;
    }

    @Override
    public int insertCoupon(CouponDto couponDto) {

        try {
            Coupon coupon = couponMapper.toCoupon(couponDto);
            coupon = couponRepo.save(coupon);

            return coupon.getCouponId();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int issueCoupon(int userId, int couponId) {
        if(couponId <= 0) {
            throw new RuntimeException("couponId is null");
        }
        if(userId <= 0) {
            throw new RuntimeException("userId is null");
        }

        Coupon coupon = couponRepo.findByCouponId(couponId);

        if(coupon == null) {
            throw new RuntimeException("coupon not found");
        }
        if(coupon.getStatus().equals(CouponStatus.ISSUED)) {
            throw new RuntimeException("coupon is already issued");
        }

        coupon.setStatus(CouponStatus.ISSUED);
        User user = userRepo.findByUserId(userId);

        if(user == null){
            throw new RuntimeException("user not found");
        }

        coupon.setUser(user);
        coupon = couponRepo.save(coupon);

        if(coupon.getStatus().equals(CouponStatus.ISSUED)) {
            return coupon.getCouponId();
        }

        return 0;
    }

    @Override
    public int useCoupon(int businessId, int couponId) {
        if(couponId <= 0) {
            throw new RuntimeException("couponId is null");
        }
        if(businessId <= 0) {
            throw new RuntimeException("businessId is null");
        }

        Coupon coupon = couponRepo.findByCouponId(couponId);

        if(coupon == null) {
            throw new RuntimeException("coupon not found");
        }
        if(coupon.getStatus().equals(CouponStatus.PENDING)) {
            throw new RuntimeException("coupon hasn't been issued yet");
        }
        if(coupon.getStatus().equals(CouponStatus.USED)) {
            throw new RuntimeException("coupon has already been used");
        }

        coupon.setStatus(CouponStatus.USED);

        Business business = businessRepo.findByBusinessId(businessId);

        if(business == null){
            throw new RuntimeException("business not found");
        }

        coupon.setBusiness(business);

        coupon = couponRepo.save(coupon);

        if(coupon.getStatus().equals(CouponStatus.USED)) {
            return coupon.getCouponId();
        }

        return 0;
    }

    @Override
    public int updateCoupon(CouponDto couponDto, int couponId) {
        Coupon coupon = couponRepo.findByCouponId(couponId);
        if(coupon == null) {
            throw new RuntimeException("coupon not found");
        }

        if(couponDto.getDescription() != null && !couponDto.getDescription().equals(coupon.getDescription())){
            coupon.setDescription(couponDto.getDescription());
        }

        if(couponDto.getDiscount() >= 0 && couponDto.getDiscount() != coupon.getDiscount()){
            coupon.setDiscount(couponDto.getDiscount());
        }

        if(coupon.getDescription() == null && couponDto.getDiscount() <= 0){
            throw new RuntimeException("Description and discount both are null");
        }

        if(couponDto.getDiscount() == coupon.getDiscount() && Objects.equals(couponDto.getDescription(), coupon.getDescription()))
        {
            throw new RuntimeException("Nothing Changed");
        }

        coupon = couponRepo.save(coupon);

        return coupon.getCouponId();
    }

    @Override
    public CouponStatus checkCoupon(int couponId) {
        Coupon coupon = couponRepo.findByCouponId(couponId);
        if(coupon == null) {
            throw new RuntimeException("coupon not found");
        }
        if(coupon.getStatus().equals(CouponStatus.ISSUED) || coupon.getStatus().equals(CouponStatus.PENDING) || coupon.getStatus().equals(CouponStatus.USED) || coupon.getStatus().equals(CouponStatus.DELETED)) {
            return coupon.getStatus();
        }
        return null;
    }

    @Override
    public int deleteCoupon(int couponId) {
        Coupon coupon = couponRepo.findByCouponId(couponId);
        coupon.setStatus(CouponStatus.DELETED);
        if(coupon.getStatus().equals(CouponStatus.DELETED)) {
            return couponRepo.save(coupon).getCouponId();
        }
        return 0;
    }
}
