package com.spotbiz.spotbiz_backend_springboot.mapper;

import com.spotbiz.spotbiz_backend_springboot.dto.CustomerPicsDto;
import com.spotbiz.spotbiz_backend_springboot.entity.CustomerPics;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerPicMapper {

    @Mapping(source = "imageUrl", target = "imageUrl")
    CustomerPics toCustomerPics(CustomerPicsDto customerPicsDto);
    CustomerPicsDto toCustomerPicsDto(CustomerPics customerPics);
}
