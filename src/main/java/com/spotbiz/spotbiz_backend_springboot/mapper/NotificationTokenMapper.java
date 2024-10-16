package com.spotbiz.spotbiz_backend_springboot.mapper;

import com.spotbiz.spotbiz_backend_springboot.dto.CouponDto;
import com.spotbiz.spotbiz_backend_springboot.dto.NotificationTokenDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Coupon;
import com.spotbiz.spotbiz_backend_springboot.entity.NotificationToken;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationTokenMapper {

    @Mapping(target = "user", expression = "java(mapUserId(notificationTokenDto.getUserId()))")
    NotificationToken toCoupon(NotificationTokenDto notificationTokenDto);

    @Mapping(source = "user.userId", target = "userId")
    NotificationTokenDto toCouponDto(NotificationToken notificationToken);

    default User mapUserId(int userId) {
        if (userId == 0) {
            return null; // Handle null or default values if necessary
        }
        User user = new User();
        user.setUserId(userId);
        return user;
    }

}

