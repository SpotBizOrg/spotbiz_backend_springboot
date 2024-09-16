package com.spotbiz.spotbiz_backend_springboot.mapper;

import com.spotbiz.spotbiz_backend_springboot.dto.CouponDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.Coupon;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CouponMapper {

    @Mapping(target = "business", expression = "java(mapBusinessId(couponDto.getBusinessId()))")
    @Mapping(target = "user", expression = "java(mapUserId(couponDto.getUserId()))")
    Coupon toCoupon(CouponDto couponDto);

    @Mapping(source = "business.businessId", target = "businessId")
    @Mapping(source = "user.userId", target = "userId")
    CouponDto toCouponDto(Coupon coupon);

    // Default method to map businessId to Business
    default Business mapBusinessId(int businessId) {
        if (businessId == 0) {
            return null; // Handle null or default values if necessary
        }
        Business business = new Business();
        business.setBusinessId(businessId);
        return business;
    }

    // Default method to map userId to User
    default User mapUserId(int userId) {
        if (userId == 0) {
            return null; // Handle null or default values if necessary
        }
        User user = new User();
        user.setUserId(userId);
        return user;
    }
}

