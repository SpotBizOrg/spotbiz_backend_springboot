package com.spotbiz.spotbiz_backend_springboot.mapper;

import com.spotbiz.spotbiz_backend_springboot.dto.SubscribeDto;
import com.spotbiz.spotbiz_backend_springboot.entity.Business;
import com.spotbiz.spotbiz_backend_springboot.entity.Subscribe;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubscribeMapper {

    @Mapping(target = "business", expression = "java(mapBusinessId(subscribeDto.getBusinessId()))")
    @Mapping(target = "user", expression = "java(mapUserId(subscribeDto.getUserId()))")
    Subscribe toSubscribe (SubscribeDto subscribeDto);

    @Mapping(source = "business.businessId", target = "businessId")
    @Mapping(source = "user.userId", target = "userId")
    SubscribeDto toSubscribeDto(Subscribe subscribe);

    default Business mapBusinessId(int businessId) {
        if (businessId == 0) {
            return null;
        }
        Business business = new Business();
        business.setBusinessId(businessId);
        return business;
    }

    default User mapUserId(int userId) {
        if (userId == 0) {
            return null;
        }
        User user = new User();
        user.setUserId(userId);
        return user;
    }
}

