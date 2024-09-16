package com.spotbiz.spotbiz_backend_springboot.mapper.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.CouponDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.Coupon;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import com.spotbiz.spotbiz_backend_springboot.mapper.CouponMapper;

public class CouponMapperImpl implements CouponMapper {

    @Override
    public Coupon toCoupon(CouponDto couponDto) {
        if (couponDto == null) {
            return null;
        }

        Coupon coupon = new Coupon();
        coupon.setCouponId(couponDto.getCouponId());
        coupon.setDateTime(couponDto.getDateTime());
        coupon.setDescription(couponDto.getDescription());
        coupon.setDiscount(couponDto.getDiscount());
        coupon.setStatus(couponDto.getStatus());

        // Map Business and User based on IDs from DTO (assuming you'll set the real objects later)
        Business business = new Business();
        business.setBusinessId(couponDto.getBusinessId());
        coupon.setBusiness(business);

        User user = new User();
        user.setUserId(couponDto.getUserId());
        coupon.setUser(user);

        return coupon;
    }

    @Override
    public CouponDto toCouponDto(Coupon coupon) {
        if (coupon == null) {
            return null;
        }

        CouponDto couponDto = new CouponDto();
        couponDto.setCouponId(coupon.getCouponId());
        couponDto.setDateTime(coupon.getDateTime());
        couponDto.setDescription(coupon.getDescription());
        couponDto.setDiscount(coupon.getDiscount());
        couponDto.setStatus(coupon.getStatus());

        // Map BusinessId and UserId from the entity's associated objects
        if (coupon.getBusiness() != null) {
            couponDto.setBusinessId(coupon.getBusiness().getBusinessId());
        }
        if (coupon.getUser() != null) {
            couponDto.setUserId(coupon.getUser().getUserId());
        }

        return couponDto;
    }
}
