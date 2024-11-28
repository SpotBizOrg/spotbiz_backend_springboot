package com.spotbiz.spotbiz_backend_springboot.mapper;

import com.spotbiz.spotbiz_backend_springboot.dto.BusinessOwnerDto;
import com.spotbiz.spotbiz_backend_springboot.dto.CustomerAdminResponseDto;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    BusinessOwnerDto toBusinessOwnerDto(User user);

    CustomerAdminResponseDto toCustomerAdminResponseDto(User user);
}
